package com.cxynw.controller.web.editor.publish;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@Controller
@Slf4j
@RequestMapping("/editor/")
public class PublishViewController {

    @PreAuthorize("hasRole('USER')")
    @ApiOperation("使用tinymce编译器的页面")
    @GetMapping("/publish.html")
    public String html(@RequestParam("groupId")BigInteger groupId,
                             Model model){
        model.addAttribute("groupId", groupId);
        if(log.isDebugEnabled()){
            log.debug("group id: [{}]", groupId);
        }
        return "editor/publish/html";
    }

}
