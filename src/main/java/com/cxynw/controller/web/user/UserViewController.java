package com.cxynw.controller.web.user;

import com.cxynw.model.does.Post;
import com.cxynw.service.AccountService;
import com.cxynw.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/user")
public class UserViewController {

    private final PostService postService;
    private final AccountService accountService;

    public UserViewController(PostService postService,
                              AccountService accountService) {
        this.postService = postService;
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile.html")
    public String profile(Model model){

        Page<Post> posts = postService.
                findByPublisher(PageRequest.of(0, 12, Sort.Direction.DESC, "updateTime"));

        model.addAttribute("posts",posts);
        model.addAttribute("user",accountService.getCurrentAccount().get());

        return "user/profile";
    }
    
}
