package com.project.demo.service;

import com.project.demo.entity.User;
import com.project.demo.service.base.BaseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户相关 Service 测试
 * 覆盖: UserService, UserGroupService, AccessTokenService
 * 同时测试 BaseService 中的核心工具方法
 */
@SpringBootTest
@DisplayName("用户模块 Service 测试")
public class UserServiceGroupTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private AccessTokenService accessTokenService;

    // ===== Spring 注入验证 =====

    @Nested
    @DisplayName("Spring 容器注入验证")
    class InjectionTest {

        @Test
        @DisplayName("UserService 注入成功")
        void userServiceNotNull() {
            assertNotNull(userService);
        }

        @Test
        @DisplayName("UserGroupService 注入成功")
        void userGroupServiceNotNull() {
            assertNotNull(userGroupService);
        }

        @Test
        @DisplayName("AccessTokenService 注入成功")
        void accessTokenServiceNotNull() {
            assertNotNull(accessTokenService);
        }
    }

    // ===== humpToLine 驼峰转下划线 =====

    @Nested
    @DisplayName("humpToLine - 驼峰转下划线")
    class HumpToLineTest {

        @Test
        @DisplayName("null 输入返回 null")
        void testNull() {
            assertNull(BaseService.humpToLine(null));
        }

        @Test
        @DisplayName("空字符串返回空字符串")
        void testEmpty() {
            assertEquals("", BaseService.humpToLine(""));
        }

        @Test
        @DisplayName("全小写无驼峰原样返回")
        void testLower() {
            assertEquals("username", BaseService.humpToLine("username"));
        }

        @Test
        @DisplayName("标准驼峰 userName → user_name")
        void testStandard() {
            assertEquals("user_name", BaseService.humpToLine("userName"));
        }

        @Test
        @DisplayName("多级驼峰 userGroupId → user_group_id")
        void testMulti() {
            assertEquals("user_group_id", BaseService.humpToLine("userGroupId"));
        }

        @Test
        @DisplayName("首字母大写 User → user")
        void testLeadingUpper() {
            assertEquals("user", BaseService.humpToLine("User"));
        }

        @Test
        @DisplayName("单字符 A → a")
        void testSingleUpper() {
            assertEquals("a", BaseService.humpToLine("A"));
        }
    }

    // ===== encryption MD5 加密 =====

    @Nested
    @DisplayName("encryption - MD5 加密")
    class EncryptionTest {

        @Test
        @DisplayName("返回 32 位小写十六进制字符串")
        void testFormat() {
            String result = userService.encryption("admin");
            assertNotNull(result);
            assertEquals(32, result.length());
            assertTrue(result.matches("[0-9a-f]{32}"));
        }

        @Test
        @DisplayName("已知值校验: admin → 21232f...")
        void testKnown() {
            assertEquals("21232f297a57a5a743894a0e4a801fc3", userService.encryption("admin"));
        }

        @Test
        @DisplayName("空字符串加密仍返回 32 位")
        void testEmpty() {
            assertEquals(32, userService.encryption("").length());
        }

        @Test
        @DisplayName("相同输入产生相同输出（幂等）")
        void testIdempotent() {
            assertEquals(userService.encryption("test123"), userService.encryption("test123"));
        }

        @Test
        @DisplayName("不同输入产生不同输出")
        void testDifferent() {
            assertNotEquals(userService.encryption("aaa"), userService.encryption("bbb"));
        }

        @Test
        @DisplayName("中文字符串加密返回 32 位")
        void testChinese() {
            assertEquals(32, userService.encryption("考研平台").length());
        }
    }

    // ===== toWhereSql SQL 条件拼接 =====

    @Nested
    @DisplayName("toWhereSql - WHERE 条件拼接")
    class ToWhereSqlTest {

        @Test
        @DisplayName("空 query 且 sqlwhere 为空 → 返回空字符串")
        void testAllEmpty() {
            assertEquals("", userService.toWhereSql(new HashMap<>(), false, null));
        }

        @Test
        @DisplayName("空 query 但有 sqlwhere → 包含 WHERE")
        void testWithSqlWhere() {
            String result = userService.toWhereSql(new HashMap<>(), false, "state = 1");
            assertTrue(result.contains("WHERE"));
            assertTrue(result.contains("state = 1"));
        }

        @Test
        @DisplayName("精确匹配（like=false）→ 生成 = 'value'")
        void testExact() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("username", "admin");
            String sql = userService.toWhereSql(q, false, null);
            assertTrue(sql.contains("WHERE"));
            assertTrue(sql.contains("= 'admin'"));
            assertFalse(sql.contains("LIKE"));
        }

        @Test
        @DisplayName("模糊匹配（like=true）→ 生成 LIKE '%value%'")
        void testLike() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("username", "admin");
            String sql = userService.toWhereSql(q, true, null);
            assertTrue(sql.contains("LIKE '%admin%'"));
        }

        @Test
        @DisplayName("_min 后缀条件 → 生成 >=")
        void testMin() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("score_min", "60");
            assertTrue(userService.toWhereSql(q, false, null).contains(">="));
        }

        @Test
        @DisplayName("_max 后缀条件 → 生成 <=")
        void testMax() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("score_max", "100");
            assertTrue(userService.toWhereSql(q, false, null).contains("<="));
        }

        @Test
        @DisplayName("多条件拼接均出现在结果中")
        void testMultiple() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("username", "admin");
            q.put("state", "1");
            String sql = userService.toWhereSql(q, false, null);
            assertTrue(sql.contains("'admin'"));
            assertTrue(sql.contains("'1'"));
        }
    }

    // ===== covertObject / covertArray =====

    @Nested
    @DisplayName("covertObject / covertArray - JSON key 转换")
    class ConvertTest {

        @Test
        @DisplayName("covertObject null → null")
        void testObjectNull() {
            assertNull(userService.covertObject(null));
        }

        @Test
        @DisplayName("covertArray null → null")
        void testArrayNull() {
            assertNull(userService.covertArray(null));
        }

        @Test
        @DisplayName("covertObject 驼峰 key 转为下划线")
        void testObjectConvert() {
            com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
            obj.put("userName", "admin");
            obj.put("userId", 1);
            com.alibaba.fastjson.JSONObject result = userService.covertObject(obj);
            assertTrue(result.containsKey("user_name"));
            assertTrue(result.containsKey("user_id"));
        }

        @Test
        @DisplayName("covertObject 空对象返回空对象")
        void testObjectEmpty() {
            com.alibaba.fastjson.JSONObject result = userService.covertObject(new com.alibaba.fastjson.JSONObject());
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("covertArray 处理含 JSONObject 的数组")
        void testArrayConvert() {
            com.alibaba.fastjson.JSONArray array = new com.alibaba.fastjson.JSONArray();
            com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
            obj.put("userId", 1);
            array.add(obj);
            com.alibaba.fastjson.JSONArray result = userService.covertArray(array);
            assertNotNull(result);
            assertEquals(1, result.size());
        }
    }
}
