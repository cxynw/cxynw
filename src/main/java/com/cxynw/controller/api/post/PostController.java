package com.cxynw.controller.api.post;

import com.cxynw.model.param.PostParam;
import com.cxynw.model.query.Edit;
import com.cxynw.model.query.Create;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Api("贴子管理")
@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('USER')")
    @ApiOperation("发布一个新的贴子")
    @PostMapping("/publish")
    public BaseSuccessResponse publish(
            @Validated(Create.class) PostParam postParam
    ){
        return postService.create(postParam);
    }

    @PreAuthorize("hasRole('USER')")
    @ApiOperation("根据ID删除贴子")
    @PostMapping("/delete")
    public BaseSuccessResponse delete(
            @ApiParam("贴的ID") @RequestParam("id")BigInteger id){
        return postService.deleteById(id);
    }

    @ApiOperation("编辑贴子")
    @PostMapping("/edit")
    public BaseSuccessResponse edit(@Validated(Edit.class) PostParam postParam){
        return postService.edit(postParam);
    }

}
