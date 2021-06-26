package com.cxynw.service.impl;

import com.cxynw.dao.PostDao;
import com.cxynw.exception.NoPermissionException;
import com.cxynw.exception.api.BaseApiRuntimeException;
import com.cxynw.manager.PostAttachmentCacheDao;
import com.cxynw.model.does.*;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.enums.FileTypeEnum;
import com.cxynw.model.enums.PostTypeEnum;
import com.cxynw.model.param.PostParam;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.model.vo.PostItemVo;
import com.cxynw.model.vo.PostVO;
import com.cxynw.repository.FileMarkRepository;
import com.cxynw.repository.PostRepository;
import com.cxynw.service.AccountService;
import com.cxynw.service.FileService;
import com.cxynw.service.PostService;
import com.cxynw.utils.DateUtils;
import com.cxynw.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostAttachmentCacheDao attachmentCacheDao;

    @Override
    public Optional<Post> findById(BigInteger id) {
        return repository.findById(id);
    }

    /**
     * 发布贴子成功
     *
     * @param postParam
     * @return
     */
    @Override
    @Transactional
    public BaseSuccessResponse<Object> create(PostParam postParam) {

        Post post = postParam.convertTo();

        Optional<User> optional = accountService.getCurrentAccount();
        User currentUser = optional.orElseThrow(()->{
            throw new NoPermissionException();
        });
        post.setPublisher(currentUser);

        Set<PostGroup> postGroupSet = new LinkedHashSet<>(1);
        PostGroup postGroup = new PostGroup();
        postGroup.setPostGroupId(postParam.getGroupId());
        postGroupSet.add(postGroup);

        post.setPostGroups(postGroupSet);

        repository.save(post);

        if(postParam.getAttachment() != null){
            FileMark fileMark = fileService.uploadFiles(postParam.getAttachment(), FileTypeEnum.ATTACHMENT);

            if(log.isDebugEnabled()){
                log.debug("query param: {}",postParam);
            }

            if(postParam.getAttachmentPassword() != null && postParam.getAttachmentPassword().length() == 32){
                String hashpw = BCrypt.hashpw(postParam.getAttachmentPassword(), BCrypt.gensalt());
                fileMarkRepository.updateDownloadPasswordById(hashpw,fileMark.getFileMarkId());
            }

            PostAttachment attachment = new PostAttachment(fileMark.getFileMarkId(),post.getPostId());
            attachmentCacheDao.insert(attachment);
        }

        return new BaseSuccessResponse<>(CodeEnum.RELEASE_POST_SUCCEED.value());
    }

    @Override
    public Page<Post> page(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    /**
     * {@link com.cxynw.controller.web.BaseViewController}
     * @param postTypeEnum
     * @param pageRequest
     * @return
     */
    @Override
    @Transactional
    public PostItemVo pagePostItemVo(PostTypeEnum postTypeEnum,PostGroup postGroup, PageRequest pageRequest) {
        Page<Post> postPage = repository.findAll((root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if(postTypeEnum != null){
                if(log.isDebugEnabled()){
                    log.debug("post type enum:[{}]",postTypeEnum);
                }
                predicates.add(criteriaBuilder.equal(root.get("postType"),postTypeEnum.getValue()));
            }
            if(postGroup != null){
                if(log.isDebugEnabled()){
                    log.debug("post group: [{}]",postGroup);
                }
                Join<Post, PostGroup> join = root.join("postGroups", JoinType.LEFT);
                Predicate postGroupId = criteriaBuilder.equal(join.get("postGroupId").as(BigInteger.class), postGroup.getPostGroupId());
                predicates.add(postGroupId);
            }
            query.where(predicates.toArray(Predicate[]::new));

            return query.getRestriction();
        },pageRequest);

        Optional<User> account = accountService.getCurrentAccount();
        return PostItemVo.generate(postPage,account);
    }

    @Override
    public Page<Post> pageByGroup(PageRequest pageRequest, PostGroup group) {
        return repository.findByPostGroups(group,pageRequest);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public BaseSuccessResponse deleteById(BigInteger id) {
        if(repository.existsById(id) == false){
            throw new BaseApiRuntimeException(CodeEnum.DELETE_POST_NOT_EXISTS,"你要删除的内容不存在");
        }
        User user = accountService.getCurrentAccount().get();
        Post post = repository.findById(id).get();
        BigInteger publisherId = post.getPublisher().getUserId();
        BigInteger userId = user.getUserId();
        if(publisherId.equals(userId)){
            repository.deleteById(post.getPostId());
            return new BaseSuccessResponse(CodeEnum.DELETE_POST_SUCCEED.value(),"删除成功");
        }
        throw new BaseApiRuntimeException(CodeEnum.DELETE_POST_FAIL,"你要删除的内容不存在");
    }

    /**
     * 这里如果不开启事务，修改将不会成功
     *
     * @param postParam
     * @return
     */
    @PreAuthorize("hasRole('USER')")
    @Override
    @Transactional
    public BaseSuccessResponse edit(PostParam postParam) {
        if(log.isDebugEnabled()){
            log.debug("post param:{}",postParam);
        }

        if(postParam.getPostId() == null ||
                repository.existsById(postParam.getPostId()) == false){
            throw new BaseApiRuntimeException(CodeEnum.POST_NOT_EXISTS,"编辑的内容不存在");
        }
        User user = accountService.getCurrentAccount().get();
        Post post = repository.findById(postParam.getPostId()).get();
        BigInteger publisherId = post.getPublisher().getUserId();
        BigInteger userId = user.getUserId();
        if(publisherId.equals(userId)){



            if(postParam.getAttachment() != null){
                FileMark fileMark = fileService.uploadFiles(postParam.getAttachment(), FileTypeEnum.ATTACHMENT);

                if(log.isDebugEnabled()){
                    log.debug("query param: {}",postParam);
                }

                if(postParam.getAttachmentPassword() != null && postParam.getAttachmentPassword().length() == 32){
                    String hashpw = BCrypt.hashpw(postParam.getAttachmentPassword(), BCrypt.gensalt());
                    fileMarkRepository.updateDownloadPasswordById(hashpw,fileMark.getFileMarkId());
                }

                attachmentCacheDao.findAllByPostId(post.getPostId()).forEach((item)->{
                    attachmentCacheDao.deleteById(item.getPostAttachmentId());
                });

                PostAttachment attachment = new PostAttachment(fileMark.getFileMarkId(),post.getPostId());
                attachmentCacheDao.insert(attachment);
            }


            post.setTitle(postParam.getTitle());
            post.setContent(postParam.getContent());
            post.setPostType(postParam.getPostType());
            post.setEditTime(DateUtils.now());
            return new BaseSuccessResponse(CodeEnum.SUCCEED.value(),"编辑成功");
        }

        throw new BaseApiRuntimeException(CodeEnum.FAIL,"你要编辑的内容不存在");
    }

    @Override
    @Transactional
    public PostItemVo searchByKeywords(String keywords,PostTypeEnum postTypeEnum,PageRequest pageRequest) {
        String[] keywordArray = keywords.split(" ");
        Page<Post> page = repository.findAll((root, query, criteriaBuilder) -> {
            Stream<Predicate> stream = Arrays.stream(keywordArray).map((item) -> {
                String text = "%" + item
                                .replace("$", "$$")
                                .replace("%", "$%")
                                .replace("_", "$_")
                                .replace("[", "$[")
                                .replace("]", "$]")
                        + "%";

                log.debug("keywords: {0}",text);

                return criteriaBuilder.like(root.get("title"), text, '$');
            });

            if(postTypeEnum != null){
                Predicate postType = criteriaBuilder.equal(root.get("postType"), postTypeEnum.getValue());
                List<Predicate> collect = stream.collect(Collectors.toList());
                collect.add(postType);
                query.where(collect.toArray(Predicate[]::new));
            }else{
                query.where(stream.toArray(Predicate[]::new));
            }

            return query.getRestriction();
        }, pageRequest);

        Optional<User> account = accountService.getCurrentAccount();
        return PostItemVo.generate(page,account);
    }

    @Override
    @Transactional
    public int addVisitsById(BigInteger id) {
        return repository.addVisitsById(id);
    }


    private final PostRepository repository;
    private final AccountService accountService;
    private final FileService fileService;
    private final FileMarkRepository fileMarkRepository;
    private final PostDao postDao;

    public PostServiceImpl(PostAttachmentCacheDao attachmentCacheDao, PostRepository repository,
                           AccountService accountService,
                           FileService fileService, FileMarkRepository fileMarkRepository,
                           PostDao postDao) {
        this.attachmentCacheDao = attachmentCacheDao;

        this.repository = repository;
        this.accountService = accountService;
        this.fileService = fileService;
        this.fileMarkRepository = fileMarkRepository;
        this.postDao = postDao;
    }

    public Page<Post> findByPublisher(PageRequest pageRequest) {
        Optional<User> optional = accountService.getCurrentAccount();
        User user = optional.orElseThrow(() -> new NoPermissionException());
        return repository.findByPublisher(user,pageRequest);
    }

}
