package com.project.demo.security;

import com.project.demo.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SQL 注入安全测试 — 文档化已知的 SQL 注入风险
 * <p>
 * 注意：这些测试用于记录 BaseService 中动态 SQL 拼接的风险，
 * 不修复漏洞，仅观察和记录应用的实际行为。
 * </p>
 */
@DisplayName("SQL 注入安全测试")
public class SqlInjectionSecurityTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("SEC-01: 查询参数中注入 OR 条件 — 观察行为")
    void testSqlInjectionInQueryParam() throws Exception {
        // 在 LIKE 搜索中注入 SQL 片段
        mockMvc.perform(get("/forum/get_list")
                        .param("title", "' OR '1'='1")
                        .param("like", "1"))
                .andExpect(status().isOk());
        // 注：BaseService.toWhereSql 直接拼接值，存在注入风险
        // 但测试仅验证应用不崩溃
    }

    @Test
    @DisplayName("SEC-02: 请求体含 DROP TABLE 负载 → 观察行为")
    void testDropTableInBody() throws Exception {
        String malicious = "{\"title\":\"test'), ('hacked'); DROP TABLE forum; --\"}";
        mockMvc.perform(post("/forum/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malicious))
                .andExpect(status().isOk());
        // 注：insert 方法直接拼接值，单引号会被原样拼接进 SQL，
        // 如果数据库允许堆叠查询(Stacked Queries)，则存在实际风险
    }

    @Test
    @DisplayName("SEC-03: URL 参数含特殊字符 → 不崩溃")
    void testSpecialCharsInUrl() throws Exception {
        mockMvc.perform(get("/forum/get_list")
                        .param("title", "test'; DROP TABLE forum;--"))
                .andExpect(status().isOk());
    }
}
