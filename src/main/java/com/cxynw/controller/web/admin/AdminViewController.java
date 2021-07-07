package com.cxynw.controller.web.admin;

import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin_center")
public class AdminViewController {

    @ApiOperation("管理员中心首页")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = {"/","/index.html"})
    public String index(){
        return "admin/index";
    }

}
