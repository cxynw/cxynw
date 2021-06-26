package com.cxynw.service;

import com.cxynw.model.enums.PostTypeEnum;
import com.cxynw.model.vo.PostItemVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithMockUser
@Slf4j
class PostServiceTest {

    @Autowired
    PostService postService;

    @Test
    void pagePostItemVo() {
        PostItemVo a = postService.pagePostItemVo(null,null, PageRequest.of(0, 12));
        log.info("post item vo[{}]",a);
        PostItemVo b = postService.pagePostItemVo(PostTypeEnum.PUBLIC_ARTICLE, null,PageRequest.of(0,12));
        log.info("post item vo[{}]",b);
        PostItemVo c = postService.pagePostItemVo(PostTypeEnum.PRIVATE_ARTICLE,null, PageRequest.of(0,12));
        log.info("post item vo[{}]",c);
    }

}