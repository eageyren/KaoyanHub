package com.project.demo.controller.mockmvc;

import com.alibaba.fastjson.JSONObject;
import com.project.demo.AbstractIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 用户模块 API 集成测试
 * 注：登录流程需要用户组(user_group)数据支持，完整流程需预置用户组数据
 */
@DisplayName("用户模块 API 集成测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApiIntegrationTest extends AbstractIntegrationTest {

    private static final String TEST_USER = "api_test_user_2024";

    @Test
    @DisplayName("API-U01: 注册新用户 → 成功")
    void testRegister() throws Exception {
        JSONObject user = new JSONObject();
        user.put("username", "api_test_user_" + System.currentTimeMillis());
        user.put("password", "test123456");
        user.put("state", 1);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user.toJSONString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(1));
    }

    @Test
    @DisplayName("API-U02: 重复注册 → 返回用户已存在")
    void testRegisterDuplicate() throws Exception {
        String username = "dup_user_" + System.currentTimeMillis();
        
        JSONObject user = new JSONObject();
        user.put("username", username);
        user.put("password", "anything");
        user.put("state", 1);

        // 先注册一次
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user.toJSONString()))
                .andExpect(status().isOk());

        // 再注册一次，预期失败
        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(user.toJSONString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error.code").value(30000));
    }

    @Test
    @DisplayName("API-U03: 不存在的用户登录 → 用户不存在")
    void testLoginNonexistent() throws Exception {
        JSONObject data = new JSONObject();
        data.put("username", "no_user_999999");
        data.put("password", "anything");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data.toJSONString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @Order(4)
    @DisplayName("API-U04: 空账号密码登录 → 返回错误")
    void testLoginEmpty() throws Exception {
        JSONObject data = new JSONObject();
        data.put("username", "");
        data.put("password", "");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data.toJSONString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @Order(5)
    @DisplayName("API-U05: 无效 token 查状态 → 未登录")
    void testStateInvalid() throws Exception {
        mockMvc.perform(get("/user/state")
                        .header("x-auth-token", "bad_token_xyz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").exists());
    }
}
