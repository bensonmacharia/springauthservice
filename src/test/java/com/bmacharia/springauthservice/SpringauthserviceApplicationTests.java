package com.bmacharia.springauthservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringauthserviceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void authenticateUser() throws Exception {
        String newLogin = "{\"username\":\"jdoe\",\"password\":\"jdoe@1234*\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newLogin)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void registerUser() throws Exception {
        String newUser = "{\"username\":\"jmine\",\"email\":\"jmine@bmacharia.com\",\"password\":\"jmine@1234*\",\"firstname\":\"Jack\",\"lastname\":\"Mine\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void returnsBadRequestForDuplicateUsername() throws Exception {
        String newUser = "{\"username\":\"jmine\",\"email\":\"jmine@bmacharia.com\",\"password\":\"jmine@1234*\",\"firstname\":\"Jack\",\"lastname\":\"Mine\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUser)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/res/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void logoutUser() throws Exception {
        String newLogout = "{\"userId\":1}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/signout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newLogout)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void refreshtoken() throws Exception {
        String newRefreshToken = "{\"refreshToken\":\"299ae1ed-fc9e-4573-ac0d-a4367911f375\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/refreshtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRefreshToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void returnsForbiddenForExpiredRefreshToken() throws Exception {
        String newRefreshToken = "{\"refreshToken\":\"299ae1ed-fc9e-4573-ac0d-a4367911f375\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/refreshtoken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRefreshToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void getArticles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/res/articles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void findArticleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/res/article/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void returnsNotFoundForGetArticleById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/res/article/99999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
