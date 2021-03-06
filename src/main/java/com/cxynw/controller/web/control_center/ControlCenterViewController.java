package com.cxynw.controller.web.control_center;

import com.cxynw.model.vo2.FileMarkItemVo;
import com.cxynw.model.vo2.UserItemVo;
import com.cxynw.service.AccountService;
import com.cxynw.service.FileMarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/control_center")
@PreAuthorize("hasRole('ADMIN')")
public class ControlCenterViewController {

    private final AccountService accountService;
    private final FileMarkService fileMarkService;

    public ControlCenterViewController(AccountService accountService, FileMarkService fileMarkService) {
        this.accountService = accountService;
        this.fileMarkService = fileMarkService;
    }

    @GetMapping("/index.html")
    public String index(){
        return "control_center/index";
    }

    @GetMapping("/users.html")
    public String users(@RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "24")Integer limit,
                        Model model){
        if(log.isDebugEnabled()){
            log.debug("page: [{}] limit: [{}]",page,limit);
        }
        UserItemVo userItemVo = accountService.findAll(PageRequest.of(page - 1, limit));
        model.addAttribute("userItemVo",userItemVo);
        return "control_center/users";
    }

    @GetMapping("/file_marks.html")
    public String fileMarks(@RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "limit",defaultValue = "24")Integer limit,
                            Model model){
        if(log.isDebugEnabled()){
            log.debug("page: [{}] limit: [{}]",page,limit);
        }
        FileMarkItemVo fileMarkItemVo = fileMarkService.findAll(PageRequest.of(page - 1, limit));
        model.addAttribute("fileMarkItemVo",fileMarkItemVo);
        return "control_center/file_marks";
    }

}
