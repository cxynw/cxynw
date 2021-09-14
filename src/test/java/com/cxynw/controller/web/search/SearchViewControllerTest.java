package com.cxynw.controller.web.search;

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
class SearchViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void keywords() throws Exception {
        String uri = "/search/{keywords}/{page}.html";
        mockMvc.perform(get(uri,"%",1)).andExpect(status().isOk());
        mockMvc.perform(get(uri,"sldkfjsad",1)).andExpect(status().isOk());
        mockMvc.perform(get(uri,"sldkfjsad",2)).andExpect(status().isOk());
        mockMvc.perform(get(uri,"sldkfjsad",0)).andExpect(status().isOk());
        mockMvc.perform(get(uri,"sldkfjsad",-1)).andExpect(status().isOk());
    }

}