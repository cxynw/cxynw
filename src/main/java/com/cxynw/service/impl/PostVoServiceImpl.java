package com.cxynw.service.impl;

import com.cxynw.model.does.Post;
import com.cxynw.model.does.User;
import com.cxynw.model.vo.PostVO;
import com.cxynw.service.AccountService;
import com.cxynw.service.PostCommentService;
import com.cxynw.service.PostService;
import com.cxynw.service.PostVoService;
import com.cxynw.utils.EntityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostVoServiceImpl implements PostVoService {

    private final PostService postService;
    private final AccountService accountService;
    private final PostCommentService postCommentService;

    public PostVoServiceImpl(PostService postService, AccountService accountService, PostCommentService postCommentService) {
        this.postService = postService;
        this.accountService = accountService;
        this.postCommentService = postCommentService;
    }

    @Override
    public Page<PostVO> page(PageRequest pageRequest) {
        Page<Post> postPage = postService.page(pageRequest);
        Optional<User> account = accountService.getCurrentAccount();
        return EntityUtils.convertToPagePostVO(postPage,account,postCommentService);
    }

}
