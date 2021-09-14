package com.cxynw.model.param;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class PostParamTest {

    @Test
    void getContent() {
        PostParam postParam = new PostParam();
        postParam.setContent("<a style='color: #e03e2d;' href='https://baidu.com' target='_blank' rel='nofollow noopener'>https://baidu.com</a>");
        log.info("post content: [{}]", postParam.getContent());
    }

}