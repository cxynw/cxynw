package com.cxynw.controller.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BaseViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void index() throws Exception {
        String uri = "/index.html";
        mockMvc.perform(get(uri)).andExpect(status().isOk());
    }

    @Test
    void defaultView() throws Exception {
        String uri = "/";
        mockMvc.perform(get(uri)).andExpect(status().isOk());
    }

    @Test
    void page() throws Exception {
        String uri = "/page/{page}.html";
        mockMvc.perform(get(uri,2)).andExpect(status().isOk());
        mockMvc.perform(get(uri,1)).andExpect(status().isOk());
        mockMvc.perform(get(uri,0)).andExpect(status().isOk());
    }

    @Test
    void group() throws Exception {
        String uri = "/group/{groupId}_{page}.html";
        mockMvc.perform(get(uri,1,2)).andExpect(status().isOk());
        mockMvc.perform(get(uri,1,1)).andExpect(status().isOk());
        mockMvc.perform(get(uri,1,0)).andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        String uri = "/login.html";
        mockMvc.perform(get(uri)).andExpect(status().isOk());
    }

    @Test
    void register() throws Exception {
        String uri = "/register.html";
        mockMvc.perform(get(uri)).andExpect(status().isOk());
    }

    @Test
    void detail() throws Exception {
        String uri = "/detail/{id}.html";
        mockMvc.perform(get(uri,1)).andExpect(status().isOk());

//        mockMvc.perform(get(uri,0)).andExpect(status().isOk());
//        mockMvc.perform(get(uri,2)).andExpect(status().isOk());
    }

    @Test
    void error() throws Exception {
        String uri = "/error.html";
        mockMvc.perform(get(uri)).andExpect(status().isOk());
        mockMvc.perform(get(uri).param("errorCode","500")).andExpect(status().is5xxServerError());
    }
}