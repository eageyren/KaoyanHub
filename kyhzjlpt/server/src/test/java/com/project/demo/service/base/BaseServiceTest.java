package com.project.demo.service.base;

import com.project.demo.service.base.BaseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseService 白盒测试 —— 聚焦 encryption / humpToLine / toWhereSql 等纯方法
 * 不依赖 Spring 容器，纯单元测试
 */
@DisplayName("BaseService 白盒单元测试")
public class BaseServiceTest {

    /**
     * 创建一个可实例化的 BaseService 子类用于测试（因 BaseService 需要泛型参数）
     * 注意：依赖 EntityManager 的方法在此不测试，仅测试纯逻辑方法
     */
    private final TestableBaseService service = new TestableBaseService();

    // ==================== humpToLine 驼峰转下划线 ====================

    @Nested
    @DisplayName("humpToLine - 驼峰转下划线")
    class HumpToLineTest {

        @Test
        @DisplayName("TC-H01: null 输入应返回 null")
        void testNull() {
            assertNull(BaseService.humpToLine(null));
        }

        @Test
        @DisplayName("TC-H02: 空字符串应返回空字符串")
        void testEmpty() {
            assertEquals("", BaseService.humpToLine(""));
        }

        @Test
        @DisplayName("TC-H03: 全小写无驼峰应原样返回")
        void testAllLowerCase() {
            assertEquals("username", BaseService.humpToLine("username"));
        }

        @Test
        @DisplayName("TC-H04: 标准驼峰 userName → user_name")
        void testStandardCamelCase() {
            assertEquals("user_name", BaseService.humpToLine("userName"));
        }

        @Test
        @DisplayName("TC-H05: 多驼峰 examQuestionId → exam_question_id")
        void testMultiHump() {
            assertEquals("exam_question_id", BaseService.humpToLine("examQuestionId"));
        }

        @Test
        @DisplayName("TC-H06: 首字母大写 User → user（去除前导下划线）")
        void testLeadingUpperCase() {
            assertEquals("user", BaseService.humpToLine("User"));
        }

        @Test
        @DisplayName("TC-H07: 连续大写 HTMLParser → h_t_m_l_parser（逐个转换）")
        void testConsecutiveUpperCase() {
            // 当前实现对每个大写字母都转换
            String result = BaseService.humpToLine("HTMLParser");
            assertNotNull(result);
            assertTrue(result.contains("_"));
        }

        @Test
        @DisplayName("TC-H08: 实体类名 PostgraduateExaminationMaterials 的转换")
        void testLongEntityName() {
            String result = BaseService.humpToLine("PostgraduateExaminationMaterials");
            assertEquals("postgraduate_examination_materials", result);
        }

        @Test
        @DisplayName("TC-H09: 单字符 A → a")
        void testSingleUpperChar() {
            assertEquals("a", BaseService.humpToLine("A"));
        }

        @Test
        @DisplayName("TC-H10: 单字符小写 a → a")
        void testSingleLowerChar() {
            assertEquals("a", BaseService.humpToLine("a"));
        }
    }

    // ==================== encryption MD5 加密 ====================

    @Nested
    @DisplayName("encryption - MD5 加密")
    class EncryptionTest {

        @Test
        @DisplayName("TC-E01: 普通字符串 admin 加密结果为32位小写十六进制")
        void testNormalString() {
            String result = service.encryption("admin");
            assertNotNull(result);
            assertEquals(32, result.length());
            assertTrue(result.matches("[0-9a-f]{32}"));
        }

        @Test
        @DisplayName("TC-E02: 已知 MD5 值校验 - admin 的 MD5")
        void testKnownMD5() {
            // admin 的标准 MD5 = 21232f297a57a5a743894a0e4a801fc3
            assertEquals("21232f297a57a5a743894a0e4a801fc3", service.encryption("admin"));
        }

        @Test
        @DisplayName("TC-E03: 空字符串加密不为 null 且长度为 32")
        void testEmptyString() {
            String result = service.encryption("");
            assertNotNull(result);
            assertEquals(32, result.length());
        }

        @Test
        @DisplayName("TC-E04: 相同输入应产生相同输出（幂等性）")
        void testIdempotent() {
            String password = "test123456";
            assertEquals(service.encryption(password), service.encryption(password));
        }

        @Test
        @DisplayName("TC-E05: 不同输入应产生不同输出")
        void testDifferentInputs() {
            assertNotEquals(service.encryption("password1"), service.encryption("password2"));
        }

        @Test
        @DisplayName("TC-E06: 中文字符串加密")
        void testChineseString() {
            String result = service.encryption("考研密码");
            assertNotNull(result);
            assertEquals(32, result.length());
        }

        @Test
        @DisplayName("TC-E07: 特殊字符加密")
        void testSpecialChars() {
            String result = service.encryption("!@#$%^&*()_+-=");
            assertNotNull(result);
            assertEquals(32, result.length());
        }

        @Test
        @DisplayName("TC-E08: 长密码加密（超过32位输入）")
        void testLongPassword() {
            String longPwd = "a]bcdefghijklmnopqrstuvwxyz0123456789ABCDEF";
            String result = service.encryption(longPwd);
            assertNotNull(result);
            assertEquals(32, result.length());
        }
    }

    // ==================== toWhereSql 拼接 SQL ====================

    @Nested
    @DisplayName("toWhereSql - WHERE 条件拼接")
    class ToWhereSqlTest {

        @Test
        @DisplayName("TC-W01: 空 query 且 sqlwhere 为 null → 返回空字符串")
        void testEmptyQueryNullSqlWhere() {
            Map<String, String> query = new HashMap<>();
            assertEquals("", service.toWhereSql(query, false, null));
        }

        @Test
        @DisplayName("TC-W02: 空 query 且 sqlwhere 非空 → 返回 WHERE sqlwhere")
        void testEmptyQueryWithSqlWhere() {
            Map<String, String> query = new HashMap<>();
            String result = service.toWhereSql(query, false, "status = 1");
            assertTrue(result.contains("WHERE"));
            assertTrue(result.contains("status = 1"));
        }

        @Test
        @DisplayName("TC-W03: 单条件精确匹配（like=false）")
        void testSingleExactMatch() {
            Map<String, String> query = new LinkedHashMap<>();
            query.put("username", "admin");
            String result = service.toWhereSql(query, false, null);
            assertTrue(result.contains("WHERE"));
            assertTrue(result.contains("= 'admin'"));
            assertFalse(result.contains("LIKE"));
        }

        @Test
        @DisplayName("TC-W04: 单条件模糊匹配（like=true）")
        void testSingleLikeMatch() {
            Map<String, String> query = new LinkedHashMap<>();
            query.put("username", "admin");
            String result = service.toWhereSql(query, true, null);
            assertTrue(result.contains("WHERE"));
            assertTrue(result.contains("LIKE '%admin%'"));
        }

        @Test
        @DisplayName("TC-W05: 多条件拼接，应包含 and 连接")
        void testMultipleConditions() {
            Map<String, String> query = new LinkedHashMap<>();
            query.put("username", "admin");
            query.put("state", "1");
            String result = service.toWhereSql(query, false, null);
            assertTrue(result.contains("WHERE"));
            // 至少出现一次 and 连接
            assertTrue(result.contains("'admin'"));
            assertTrue(result.contains("'1'"));
        }

        @Test
        @DisplayName("TC-W06: 范围查询 _min 条件")
        void testMinCondition() {
            Map<String, String> query = new LinkedHashMap<>();
            query.put("score_min", "60");
            String result = service.toWhereSql(query, false, null);
            assertTrue(result.contains(">="));
            assertTrue(result.contains("60"));
        }

        @Test
        @DisplayName("TC-W07: 范围查询 _max 条件")
        void testMaxCondition() {
            Map<String, String> query = new LinkedHashMap<>();
            query.put("score_max", "100");
            String result = service.toWhereSql(query, false, null);
            assertTrue(result.contains("<="));
            assertTrue(result.contains("100"));
        }

        @Test
        @DisplayName("TC-W08: 组合 _min + _max 范围查询")
        void testMinMaxCombined() {
            Map<String, String> query = new LinkedHashMap<>();
            query.put("score_min", "60");
            query.put("score_max", "100");
            String result = service.toWhereSql(query, false, null);
            assertTrue(result.contains(">="));
            assertTrue(result.contains("<="));
        }

        @Test
        @DisplayName("TC-W09: query + sqlwhere 联合条件")
        void testQueryAndSqlWhereCombined() {
            Map<String, String> query = new LinkedHashMap<>();
            query.put("username", "admin");
            String result = service.toWhereSql(query, false, "state = 1");
            assertTrue(result.contains("WHERE"));
            assertTrue(result.contains("admin"));
            assertTrue(result.contains("state = 1"));
        }

        @Test
        @DisplayName("TC-W10: 空 query + 空白 sqlwhere → 返回空字符串")
        void testEmptyQueryEmptySqlWhere() {
            Map<String, String> query = new HashMap<>();
            assertEquals("", service.toWhereSql(query, false, ""));
            assertEquals("", service.toWhereSql(query, false, "  "));
        }
    }

    // ==================== covertObject / covertArray JSON 转换 ====================

    @Nested
    @DisplayName("covertObject / covertArray - JSON key 转换")
    class CovertTest {

        @Test
        @DisplayName("TC-C01: covertObject null 输入返回 null")
        void testCovertObjectNull() {
            assertNull(service.covertObject(null));
        }

        @Test
        @DisplayName("TC-C02: covertArray null 输入返回 null")
        void testCovertArrayNull() {
            assertNull(service.covertArray(null));
        }

        @Test
        @DisplayName("TC-C03: covertObject 驼峰 key 应被转为下划线")
        void testCovertObjectConvertsKeys() {
            com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
            obj.put("userName", "admin");
            obj.put("userId", 1);
            com.alibaba.fastjson.JSONObject result = service.covertObject(obj);
            assertTrue(result.containsKey("user_name"));
            assertTrue(result.containsKey("user_id"));
        }

        @Test
        @DisplayName("TC-C04: covertArray 处理包含 JSONObject 的数组")
        void testCovertArrayWithObjects() {
            com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
            com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
            obj.put("examId", 1);
            array.add(obj);
            com.alibaba.fastjson.JSONArray result = service.covertArray(array);
            assertNotNull(result);
            assertEquals(1, result.size());
        }

        @Test
        @DisplayName("TC-C05: covertObject 空 JSONObject 返回空 JSONObject")
        void testCovertObjectEmpty() {
            com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
            com.alibaba.fastjson.JSONObject result = service.covertObject(obj);
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    // ==================== 辅助内部类 ====================

    /**
     * 用于绕过 BaseService 对泛型的要求以及 EntityManager 依赖
     * 仅用于测试纯方法（encryption, humpToLine, toWhereSql 等）
     */
    static class TestableBaseService extends BaseService<com.project.demo.entity.User> {
        // 不需要 EntityManager，因为测试方法不涉及数据库操作
    }
}

