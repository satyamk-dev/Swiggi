package com.nt.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.entityDTO.RequestDTO;
import com.nt.entityDTO.ResponseDTO;
import com.nt.service.ICommentListService;
import com.nt.serviceimpl.CustomerServiceImpl;
@WebMvcTest(SwiggiController.class)
public class TestControllerMethods {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CustomerServiceImpl cr;

    @MockBean
    private ICommentListService comments; // ✅ Add this line

    @Test
    public void TestUserRegister() throws Exception {
        RequestDTO req = new RequestDTO();
        req.setId(1L);
        req.setFirstName("Satyam");
        req.setLastName("Kumar");
        req.setEmail("abc@gmail.com");
        req.setMobile(2345654322L);
        req.setPassword("Abc@123");

        ResponseDTO res = new ResponseDTO();
        res.setId(1L);
        res.setFirstName("Satyam");
        res.setLastName("Kumar");
        res.setEmail("abc@gmail.com");
        res.setMobile(2345654322L);

        when(cr.RegisterCustomer(req)).thenReturn(res);

        mockMvc.perform(post("/swiggi/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Satyam"))
                .andExpect(jsonPath("$.lastName").value("Kumar")) // small typo fix (capital K)
                .andExpect(jsonPath("$.mobile").value(2345654322L))
                .andExpect(jsonPath("$.email").value("abc@gmail.com"));
    }
}
