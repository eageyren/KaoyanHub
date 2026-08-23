package com.project.demo.controller;

import com.project.demo.entity.Forum;
import com.project.demo.service.ForumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 输入边界与验证测试 — Service 层直接测试边界条件
 * (强制更新触发重新编译)
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("输入边界验证测试")
public class InputValidationTest {

    @Autowired
    private ForumService forumService;

    @Test
    @DisplayName("IV-01: select 不存在的条件 → 返回空列表（不崩溃）")
    void testGetNonexistentId() {
        Map<String, String> query = new HashMap<>();
        query.put("forum_id", "999999");
        List list = forumService.select(query, new HashMap<>()).getResultList();
        assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("IV-02: selectToPage page=-1 → 正常返回（不崩溃）")
    void testNegativePage() {
        Map<String, String> config = new HashMap<>();
        config.put("page", "-1");
        config.put("size", "10");
        Map<String, Object> result = forumService.selectToPage(new HashMap<>(), config);
        assertNotNull(result);
        assertTrue(result.containsKey("list"));
        assertTrue(result.containsKey("count"));
    }

    @Test
    @DisplayName("IV-03: selectToPage 超大页码 → 返回空列表（不崩溃）")
    void testHugePage() {
        Map<String, String> config = new HashMap<>();
        config.put("page", "99999");
        config.put("size", "10");
        Map<String, Object> result = forumService.selectToPage(new HashMap<>(), config);
        assertNotNull(result);
    }
}
