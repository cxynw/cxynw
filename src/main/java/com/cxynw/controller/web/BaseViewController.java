package com.cxynw.controller.web;

import com.cxynw.manager.FileMarkCacheDao;
import com.cxynw.model.does.*;
import com.cxynw.model.enums.PostTypeEnum;
import com.cxynw.model.vo2.PostAttachmentVO;
import com.cxynw.model.vo2.PostItemVo;
import com.cxynw.service.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@Slf4j
@Controller
@RequestMapping("/")
public class BaseViewController {

    @GetMapping(value = {"/index.html"})
    public String index(){return "forward:/page/1.html";}

    @GetMapping(value = "/")
    public String defaultView(){
        return "forward:/page/1.html";
    }

    @GetMapping("/page/{page:[\\d]+}.html")
    @Transactional
    public String page(Model model,
                       @Valid @PathVariable("page")Integer page){
        if(page <= 0){
            page = 1;
        }

        Page<PostGroup> groups = postGroupService.page(PageRequest.of(0, 24, Sort.Direction.DESC, "updateTime"));
        PostItemVo postItemVo = postService.pagePostItemVo(PostTypeEnum.PUBLIC_ARTICLE,null,PageRequest.of(page - 1, 12, Sort.Direction.DESC, "updateTime"));
        model.addAttribute("postItemVo", postItemVo);
        model.addAttribute("groups",groups);

        if(log.isDebugEnabled()){
            log.debug("page: {}",page);
        }

        return "page";
    }

    @GetMapping("/group/{groupId}_{page}.html")
    @Transactional
    public String group(@PathVariable("page")Integer page,
                        @PathVariable("groupId")BigInteger groupId,
                        Model model) throws Throwable {
        if(page <= 0){
            page = 1;
        }

        Optional<PostGroup> id = postGroupService.findById(groupId);
        PostGroup group = id.orElseThrow(() -> {
            log.debug("not found post group by id :[{}]",groupId);
            return new Throwable("?????????????????????");
        });

        Page<PostGroup> groups = postGroupService.page(PageRequest.of(0, 24, Sort.Direction.DESC, "createTime"));

        PostItemVo postItemVo = postService.pagePostItemVo(PostTypeEnum.PUBLIC_ARTICLE,group,PageRequest.of(page - 1, 12, Sort.Direction.DESC, "updateTime"));
        model.addAttribute("postItemVo", postItemVo);
        model.addAttribute("groups",groups);
        model.addAttribute("currentGroup",group);

        if(log.isDebugEnabled()){
            log.debug("/group/{}_{}.html",groupId,page);
        }

        return "group";
    }

    @GetMapping("/login.html")
    public String login(){return "login";}

    @GetMapping("/register.html")
    public String register(){return "register";}

    @GetMapping(value = "/detail/{id:[\\d]+}.html")
    @Transactional
    public String detail(@PathVariable("id")BigInteger id,
                         Model model){

        Optional<Post> optional = postService.findById(id);
        Post post = optional.orElseThrow(() -> new RuntimeException("?????????????????????"));
        if(post.getPostType() != PostTypeEnum.PUBLIC_ARTICLE.getValue()){
            Optional<User> currentAccount = accountService.getCurrentAccount();
            User user = currentAccount.orElseThrow(() -> new RuntimeException("?????????????????????"));
            if(post.getPublisher().getUserId().equals(user.getUserId())==false){
                throw new RuntimeException("?????????????????????");
            }
        }

        Hibernate.initialize(post.getPublisher().getActors());
        postService.addVisitsById(post.getPostId());

        List<PostAttachment> attachments = attachmentService.findByPostId(post.getPostId());
        PostAttachmentVO[] attachmentVOS = attachments.stream().map((item) -> {
            PostAttachmentVO vo = new PostAttachmentVO();
            FileMark fileMark = fileMarkCacheDao.findById(item.getFileMarkId()).get();
            vo.setFileMark(fileMark);
            return vo;
        }).toArray(PostAttachmentVO[]::new);

        List<Post> aboutPosts = postService.page(PageRequest.of(0,12, Sort.Direction.ASC,"createTime")).getContent();

        String clean = Jsoup.clean(post.getContent(), Whitelist.none());
        if(clean.length() > 128){
            clean = clean.substring(0,128);
        }

        model.addAttribute("description", clean);
        model.addAttribute("post",post);
        model.addAttribute("attachments",attachmentVOS);
        model.addAttribute("aboutPosts",aboutPosts);

        if(log.isDebugEnabled()){
            log.debug("??????????????? {0}",attachments.size());
        }

        return "detail";
    }

    @GetMapping("/error.html")
    public String error(@RequestParam(value = "errorCode",required = false)Integer errorCode,
                        HttpServletResponse response) {
        if(errorCode == null){
            response.setStatus(HttpStatus.OK.value());
        }else{
            response.setStatus(errorCode);
        }
        return "error";
    }

    private final PostService postService;
    private final PostGroupService postGroupService;
    private final UserService userService;
    private final AccountService accountService;
    private final PostAttachmentService attachmentService;
    private final FileMarkCacheDao fileMarkCacheDao;

    public BaseViewController(PostService postService,
                              PostGroupService postGroupService,
                              UserService userService,
                              AccountService accountService,
                              PostAttachmentService attachmentService,
                              FileMarkCacheDao fileMarkCacheDao) {
        this.postService = postService;
        this.postGroupService = postGroupService;
        this.userService = userService;
        this.accountService = accountService;
        this.attachmentService = attachmentService;
        this.fileMarkCacheDao = fileMarkCacheDao;
    }

}
