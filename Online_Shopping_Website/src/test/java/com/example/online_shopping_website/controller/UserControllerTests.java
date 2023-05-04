package com.example.online_shopping_website.controller;

import com.example.online_shopping_website.entity.User;
import com.example.online_shopping_website.util.JsonResult;
import com.example.online_shopping_website.controller.*;
import com.example.online_shopping_website.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void registerTest() throws Exception{
        MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/api/userRegister")
                                            .param("usertype","1")
                                            .param("username","jack")
                                            .param("password", "123456")
                                            .param("phone","13600774472")
                                            .param("email","abc@qq.com")
                                            .param("idnum","350582190001015046")
                                            )
                                    .andReturn();

        System.out.println(mvcResult.toString());

    }

    @Test
    public void loginTest() {

    }

    @Test
    public void getUserInfoTest(){
        
    }
    
    @Test
    public void setUserInfoTest(){
        
    }
    
}
