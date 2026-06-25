package com.project.demo.service;

import com.project.demo.service.base.BaseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 系统管理模块 Service 测试
 * 覆盖: NoticeService, NavAdminService, SlidesService, AuthService, SystemUserService, UploadService
 */
@SpringBootTest
@DisplayName("系统管理模块 Service 测试")
public class SystemServiceGroupTest {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NavAdminService navAdminService;

    @Autowired
    private SlidesService slidesService;

    @Autowired
    private AuthService authService;

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private UploadService uploadService;

    // ===== Spring 注入验证 =====

    @Nested
    @DisplayName("Spring 容器注入验证")
    class InjectionTest {

        @Test
        @DisplayName("NoticeService 注入成功")
        void noticeServiceNotNull() {
            assertNotNull(noticeService);
        }

        @Test
        @DisplayName("NavAdminService 注入成功")
        void navAdminServiceNotNull() {
            assertNotNull(navAdminService);
        }

        @Test
        @DisplayName("SlidesService 注入成功")
        void slidesServiceNotNull() {
            assertNotNull(slidesService);
        }

        @Test
        @DisplayName("AuthService 注入成功")
        void authServiceNotNull() {
            assertNotNull(authService);
        }

        @Test
        @DisplayName("SystemUserService 注入成功")
        void systemUserServiceNotNull() {
            assertNotNull(systemUserService);
        }

        @Test
        @DisplayName("UploadService 注入成功")
        void uploadServiceNotNull() {
            assertNotNull(uploadService);
        }
    }

    // ===== humpToLine 系统管理字段验证 =====

    @Nested
    @DisplayName("humpToLine - 系统字段名转换")
    class HumpToLineTest {

        @Test
        @DisplayName("noticeId → notice_id")
        void testNoticeId() {
            assertEquals("notice_id", BaseService.humpToLine("noticeId"));
        }

        @Test
        @DisplayName("navAdminId → nav_admin_id")
        void testNavAdminId() {
            assertEquals("nav_admin_id", BaseService.humpToLine("navAdminId"));
        }

        @Test
        @DisplayName("slidesId → slides_id")
        void testSlidesId() {
            assertEquals("slides_id", BaseService.humpToLine("slidesId"));
        }

        @Test
        @DisplayName("systemUserId → system_user_id")
        void testSystemUserId() {
            assertEquals("system_user_id", BaseService.humpToLine("systemUserId"));
        }

        @Test
        @DisplayName("imgUrl → img_url")
        void testImgUrl() {
            assertEquals("img_url", BaseService.humpToLine("imgUrl"));
        }
    }

    // ===== toWhereSql 系统管理场景 =====

    @Nested
    @DisplayName("toWhereSql - 系统管理场景条件拼接")
    class ToWhereSqlTest {

        @Test
        @DisplayName("按公告标题模糊搜索")
        void testNoticeTitleLike() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("title", "通知");
            String sql = noticeService.toWhereSql(q, true, null);
            assertTrue(sql.contains("LIKE '%通知%'"));
        }

        @Test
        @DisplayName("按导航 type 精确查询")
        void testNavType() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("type", "admin");
            String sql = navAdminService.toWhereSql(q, false, null);
            assertTrue(sql.contains("= 'admin'"));
        }

        @Test
        @DisplayName("按幻灯片位置精确查询")
        void testSlidesByPosition() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("position", "home");
            String sql = slidesService.toWhereSql(q, false, null);
            assertTrue(sql.contains("= 'home'"));
        }

        @Test
        @DisplayName("空条件返回空字符串")
        void testEmpty() {
            assertEquals("", noticeService.toWhereSql(new HashMap<>(), false, null));
        }

        @Test
        @DisplayName("系统用户按用户名查询")
        void testSystemUserByName() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("userName", "sysAdmin");
            String sql = systemUserService.toWhereSql(q, false, null);
            assertTrue(sql.contains("user_name"));
            assertTrue(sql.contains("= 'sysAdmin'"));
        }
    }

    // ===== encryption 系统管理模块 =====

    @Nested
    @DisplayName("encryption - 系统管理加密验证")
    class EncryptionTest {

        @Test
        @DisplayName("noticeService.encryption 返回 32 位 MD5")
        void testFormat() {
            assertEquals(32, noticeService.encryption("notice_pwd").length());
        }

        @Test
        @DisplayName("所有系统 Service encryption 结果一致")
        void testConsistency() {
            String input = "sys_password";
            String expected = noticeService.encryption(input);
            assertEquals(expected, navAdminService.encryption(input));
            assertEquals(expected, slidesService.encryption(input));
            assertEquals(expected, uploadService.encryption(input));
        }
    }
}
