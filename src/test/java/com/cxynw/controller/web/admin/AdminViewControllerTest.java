package com.cxynw.controller.web.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin",roles = "ADMIN")
class AdminViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void index() throws Exception {
        String uri = "/admin_center/";
        String uri2 = "/admin_center/index.html";

        mockMvc.perform(get(uri)).andExpect(status().isOk());
        mockMvc.perform(get(uri2)).andExpect(status().isOk());
    }
}