package com.whendada.springdemo;

import static org.hamcrest.Matchers.containsString;
import static
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHmoePage() throws Exception {

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(containsString("Hello World")));

    }
}
