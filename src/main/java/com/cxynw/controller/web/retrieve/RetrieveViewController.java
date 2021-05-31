package com.cxynw.controller.web.retrieve;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/retrieve")
@Slf4j
public class RetrieveViewController {

    @GetMapping("/username.html")
    public String username(){
        if(log.isTraceEnabled()){
            log.trace("request web by uri /retrieve/username.html");
        }
        return "retrieve/username";
    }

}
