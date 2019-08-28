package com.oocl.web.sampleWebApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleWebAppApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() throws Exception {
        this.mockMvc.perform(get("/hello/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hello")));
    }

    @Test
    public void getBadResponseStatus() throws Exception {
        this.mockMvc.perform(get("/hello/sayHello"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("hello")));
    }

    @Test
    public void testReturnEmployee() throws Exception {
        this.mockMvc.perform(get("/hello/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("like")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(22)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender", CoreMatchers.is("male")));

    }

    @Test
    public void testPostReturnEmployee() throws Exception {
        Employee employee = new Employee("5", "1", 20, "male");
        String postString = objectMapper.writeValueAsString(employee);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/hello/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is("5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender", CoreMatchers.is("male")));

    }
}
