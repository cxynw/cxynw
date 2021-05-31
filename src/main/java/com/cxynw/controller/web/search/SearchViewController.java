package com.cxynw.controller.web.search;

import com.cxynw.model.vo.PostVO;
import com.cxynw.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/search")
public class SearchViewController {

    private final PostService postService;

    public SearchViewController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{keywords}/{page}.html")
    public String keywords(
            @PathVariable("keywords") String keywords,
            @PathVariable("page")Integer page,
            Model model){

        String useKeywords = keywords;
        if(keywords.length() > 64){
            useKeywords = useKeywords.substring(64);
        }
        int usePage = page;
        if(page < 1){
            usePage = 1;
        }
        Page<PostVO> postVOPage = postService.searchByKeywords(
                useKeywords,
                PageRequest.of(usePage-1, 24, Sort.Direction.DESC, "createTime")
        );

        model.addAttribute("posts",postVOPage);
        model.addAttribute("keywords",useKeywords);

        return "search";
    }

}
