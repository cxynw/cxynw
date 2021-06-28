package com.cxynw.controller.web.control_center;

import com.cxynw.model.vo.UserItemVo;
import com.cxynw.service.AccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/control_center")
public class ControlCenterViewController {

    private final AccountService accountService;

    public ControlCenterViewController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/index.html")
    public String index(@RequestParam(value = "page",defaultValue = "1")Integer page, Model model){
        UserItemVo userItemVo = accountService.findAll(PageRequest.of(page - 1, 24));
        model.addAttribute("userItemVo",userItemVo);
        return "control_center/index";
    }

}
