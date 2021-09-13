package com.cxynw.controller.api.post.comment;

import com.cxynw.model.does.PostComment;
import com.cxynw.model.enums.CodeEnum;
import com.cxynw.model.query.PostCommentInsert;
import com.cxynw.model.query.PostCommentQuery;
import com.cxynw.model.response.BaseSuccessResponse;
import com.cxynw.model.vo2.PostCommentVO;
import com.cxynw.service.PostCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/post/comment")
public class PostCommentController {

    private final PostCommentService commentService;

    public PostCommentController(PostCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/publish")
    @ApiOperation("添加评论")
    public BaseSuccessResponse publish(
            @Valid @RequestBody PostCommentInsert query){
        Optional<PostComment> comment = commentService.insert(query);
        if(comment.isEmpty()){
            return new BaseSuccessResponse(CodeEnum.FAIL,"添加评论失败");
        }
        return new BaseSuccessResponse(CodeEnum.SUCCEED,"添加评论成功");
    }

    @PostMapping("/page")
    @ApiOperation("获取评论")
    public BaseSuccessResponse page(@Valid @RequestBody PostCommentQuery commentQuery){
        PostCommentVO data = commentService.pageByPostIdWithVO(
                commentQuery.getPostId(),
                PageRequest.of(commentQuery.getPage()-1, 12, Sort.Direction.DESC, "createTime")
        );
        return  new BaseSuccessResponse(CodeEnum.SUCCEED,"查询成功",data);
    }

}
