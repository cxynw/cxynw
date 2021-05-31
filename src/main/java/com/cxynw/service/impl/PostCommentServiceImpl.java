package com.cxynw.service.impl;

import com.cxynw.dao.PostCommentDao;
import com.cxynw.exception.api.BaseApiRuntimeException;
import com.cxynw.model.does.Post;
import com.cxynw.model.does.PostComment;
import com.cxynw.model.does.User;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.query.PostCommentInsert;
import com.cxynw.model.vo.PostCommentVO;
import com.cxynw.repository.PostCommentRepository;
import com.cxynw.service.AccountService;
import com.cxynw.service.PostCommentService;
import com.cxynw.service.UserService;
import com.cxynw.service.base.BaseServiceImpl;
import com.cxynw.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
public class PostCommentServiceImpl
extends BaseServiceImpl<PostComment,BigInteger, PostCommentRepository>
implements PostCommentService {

    private final PostCommentDao commentDao;
    private final UserService userService;
    private final AccountService accountService;
    private PostCommentService postCommentService;

    @Autowired
    public void setPostCommentService(PostCommentService postCommentService){
        this.postCommentService = postCommentService;
    }

    public PostCommentServiceImpl(PostCommentRepository repository
            , PostCommentDao commentDao, UserService userService, AccountService accountService) {
        super(repository);
        this.commentDao = commentDao;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public Page<PostComment> pageByPost(Post post, Pageable pageable) {
        return repository.findAllByPost(post,pageable);
    }

    @Override
    public Page<PostComment> pageByPostId(BigInteger id,Pageable pageable) {
        Post post = EntityUtils.generatePostById(id);
        return repository.findAllByPost(post,pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    @Override
    public Optional<PostComment> insert(PostCommentInsert postCommentInsert) {
        User user = accountService.getCurrentAccount().get();
        PostComment comment = new PostComment(
                EntityUtils.generateUserById(user.getUserId()),
                EntityUtils.generatePostById(postCommentInsert.getPostId()),
                postCommentInsert.getComment()
        );
        if(postCommentInsert.getParentCommentId() != null){
            comment.setParentComment(
                    EntityUtils.generatePostCommentById(postCommentInsert.getParentCommentId())
            );
        }
        try{
            return commentDao.insert(comment);
        }catch (Throwable e){
            throw new BaseApiRuntimeException(CodeEnum.FAIL,"评论失败");
        }
    }

    @Override
    public PostCommentVO pageByPostIdWithVO(BigInteger id, Pageable pageable) {
        Page<PostComment> page = postCommentService.pageByPostId(id, pageable);
        PostCommentVO vo = new PostCommentVO();
        vo.setHasPrevious(page.hasPrevious());
        vo.setHasNext(page.hasNext());
        vo.setPreviousPage(page.getNumber());
        vo.setNextPage(page.getNumber()+2);

        PostCommentVO.CommentVO[] array = page.getContent().stream().map((item) -> {
            String nickname = item.getSender().getNickname();
            String content = item.getCommentContent();


            String avatar;
            if(item.getSender().getAvatarId() == null){
                avatar = String.format("/avatar/generate/%s",URLEncoder.encode(nickname, StandardCharsets.UTF_8));
            }else{
                avatar = String.format("/avatar/%s",item.getSender().getAvatarId());
            }

            PostCommentVO.CommentVO commentVO = new PostCommentVO.CommentVO(
                    nickname,
                    avatar,
                    content,
                    item.getCreateTime()
            );
            return commentVO;
        }).toArray(PostCommentVO.CommentVO[]::new);
        vo.setComments(array);
        return vo;
    }

}
