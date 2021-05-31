package com.cxynw.controller.web.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin_center")
public class AdminViewController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"/","/index.html"})
    public String index(){
        return "admin/index";
    }

}
