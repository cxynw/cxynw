package com.cxynw.controller.web.editor.edit;

import com.cxynw.model.does.Post;
import com.cxynw.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigInteger;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/editor")
public class EditViewController {

    private final PostService postService;

    public EditViewController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('USER')")
    @ApiOperation("使用tinymce编译器的页面")
    @GetMapping("/edit/{id:[0-9]+}.html")
    public String html(
            @PathVariable("id")BigInteger postId,
            Model model
    ){
        if(log.isDebugEnabled()){
            log.debug("post id: [{}]",postId);
        }
        Optional<Post> optional = postService.findById(postId);
        if(optional.isEmpty()){
            log.debug("not found post by id: [{}]",postId);
            throw new RuntimeException("内容没有找到");
        }
        model.addAttribute("post",optional.get());
        return "editor/edit/html";
    }

}
