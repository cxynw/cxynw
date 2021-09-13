package com.cxynw.controller.api.user;

import com.cxynw.model.does.Post;
import com.cxynw.model.vo.PostItemPageVo;
import com.cxynw.service.AccountService;
import com.cxynw.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping(path = "/user/")
@PreAuthorize("hasRole('USER')")
public class UserPostController {

    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;

    @GetMapping("posts")
    public PostItemPageVo posts(Integer page,@RequestParam(value = "keywords",required = false) String keywords){
        if(page < 1){
            page = 1;
        }

        PageRequest pageRequest = PageRequest.of(page - 1, 24, Sort.Direction.DESC, "createTime");
        Page<Post> postPage;
        if(keywords == null){
            postPage = postService.findByPublisher(pageRequest);
        }else{
            postPage = postService.searchByKeywords(keywords, null, accountService.getCurrentAccount().get(), pageRequest);
        }
        return new PostItemPageVo(postPage);
    }


}
