package com.cxynw.controller.api.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin",roles = "USER")
class UserPostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testPosts() throws Exception {
        mockMvc.perform(get("/user/posts")).andExpect(ResultMatcher.matchAll(
                status().isOk()
        ));
        mockMvc.perform(get("/user/posts").param("page","1")).andExpect(ResultMatcher.matchAll(
                status().isOk()
        ));
        mockMvc.perform(get("/user/posts").param("page","1").param("keywords","1 2 3 4 5 6 7 8 9 10")).andExpect(ResultMatcher.matchAll(
                status().isOk()
        ));
    }
}