package com.project.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.demo.Application;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 各业务模块黑盒集成测试 —— 论坛、资料、院校、考试、公告等模块
 * 需要数据库连接
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@DisplayName("业务模块 黑盒集成测试")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModulesTest {

    @Autowired
    private MockMvc mockMvc;

    // ==================== 论坛模块 ====================

    @Test
    @Order(1)
    @DisplayName("IT-FM01: 获取论坛帖子列表 → 返回 list")
    void testGetForumList() throws Exception {
        mockMvc.perform(get("/forum/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").exists());
    }

    @Test
    @Order(2)
    @DisplayName("IT-FM02: 获取论坛帖子列表（分页）")
    void testGetForumListPaginated() throws Exception {
        mockMvc.perform(get("/forum/get_list")
                .param("page", "1")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").exists())
                .andExpect(jsonPath("$.result.count").exists());
    }

    @Test
    @Order(3)
    @DisplayName("IT-FM03: 获取论坛分类列表")
    void testGetForumTypeList() throws Exception {
        mockMvc.perform(get("/forum_type/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    // ==================== 评论模块 ====================

    @Test
    @Order(4)
    @DisplayName("IT-CM01: 获取评论列表")
    void testGetCommentList() throws Exception {
        mockMvc.perform(get("/comment/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    // ==================== 考研资料模块 ====================

    @Test
    @Order(5)
    @DisplayName("IT-PM01: 获取考研资料列表 → 返回 list")
    void testGetMaterialsList() throws Exception {
        mockMvc.perform(get("/postgraduate_examination_materials/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").exists());
    }

    @Test
    @Order(6)
    @DisplayName("IT-PM02: 获取考研资料列表（分页）")
    void testGetMaterialsListPaginated() throws Exception {
        mockMvc.perform(get("/postgraduate_examination_materials/get_list")
                .param("page", "1")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").exists());
    }

    @Test
    @Order(7)
    @DisplayName("IT-PM03: 获取资料类型列表")
    void testGetDataTypeList() throws Exception {
        mockMvc.perform(get("/data_type/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    // ==================== 报考院校模块 ====================

    @Test
    @Order(8)
    @DisplayName("IT-CU01: 获取报考院校列表")
    void testGetCollegesList() throws Exception {
        mockMvc.perform(get("/colleges_and_universities/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").exists());
    }

    @Test
    @Order(9)
    @DisplayName("IT-CU02: 获取报考院校列表（分页参数）")
    void testGetCollegesListPaged() throws Exception {
        mockMvc.perform(get("/colleges_and_universities/get_list")
                .param("page", "1")
                .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.list").exists())
                .andExpect(jsonPath("$.result.count").exists());
    }

    // ==================== 考试系统 ====================

    @Test
    @Order(10)
    @DisplayName("IT-EX01: 获取考试列表")
    void testGetExamList() throws Exception {
        mockMvc.perform(get("/exam/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(11)
    @DisplayName("IT-EX02: 获取题库列表")
    void testGetExamQuestionList() throws Exception {
        mockMvc.perform(get("/exam_question/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(12)
    @DisplayName("IT-EX03: 获取用户答题记录列表")
    void testGetUserAnswerList() throws Exception {
        mockMvc.perform(get("/user_answer/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    // ==================== 在线问答 ====================

    @Test
    @Order(13)
    @DisplayName("IT-OQ01: 获取在线提问列表")
    void testGetOnlineQuestionsList() throws Exception {
        mockMvc.perform(get("/online_questions/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(14)
    @DisplayName("IT-OQ02: 获取在线答疑列表")
    void testGetOnlineQaList() throws Exception {
        mockMvc.perform(get("/online_qa/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    // ==================== 公告系统 ====================

    @Test
    @Order(15)
    @DisplayName("IT-NT01: 获取公告列表")
    void testGetNoticeList() throws Exception {
        mockMvc.perform(get("/notice/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(16)
    @DisplayName("IT-NT02: 获取轮播图列表")
    void testGetSlidesList() throws Exception {
        mockMvc.perform(get("/slides/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    // ==================== 系统管理 ====================

    @Test
    @Order(17)
    @DisplayName("IT-SY01: 获取用户组列表")
    void testGetUserGroupList() throws Exception {
        mockMvc.perform(get("/user_group/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(18)
    @DisplayName("IT-SY02: 获取权限配置列表")
    void testGetAuthList() throws Exception {
        mockMvc.perform(get("/auth/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(19)
    @DisplayName("IT-SY03: 获取导航菜单列表")
    void testGetNavAdminList() throws Exception {
        mockMvc.perform(get("/nav_admin/get_list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    // ==================== 统计接口 ====================

    @Test
    @Order(20)
    @DisplayName("IT-ST01: 论坛帖子统计数量")
    void testForumCount() throws Exception {
        mockMvc.perform(get("/forum/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(21)
    @DisplayName("IT-ST02: 考研资料统计数量")
    void testMaterialsCount() throws Exception {
        mockMvc.perform(get("/postgraduate_examination_materials/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(22)
    @DisplayName("IT-ST03: 报考院校统计数量")
    void testCollegesCount() throws Exception {
        mockMvc.perform(get("/colleges_and_universities/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").exists());
    }
}

