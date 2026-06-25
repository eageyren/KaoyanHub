package com.project.demo.controller;

import com.project.demo.controller.UserController;
import com.project.demo.entity.AccessToken;
import com.project.demo.entity.User;
import com.project.demo.entity.UserGroup;
import com.project.demo.service.AccessTokenService;
import com.project.demo.service.UserGroupService;
import com.project.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * UserController 白盒测试 —— Mock Service 层，聚焦 Controller 分支覆盖
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserController 白盒测试")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AccessTokenService tokenService;

    @Mock
    private UserGroupService userGroupService;

    @InjectMocks
    private UserController userController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Query mockQuery;

    @BeforeEach
    void setUpOuter() {
        org.springframework.test.util.ReflectionTestUtils.setField(userController, "tokenService", tokenService);
        org.springframework.test.util.ReflectionTestUtils.setField(userController, "userGroupService", userGroupService);
    }

    // ==================== 注册 ====================

    @Nested
    @DisplayName("register - 用户注册")
    class RegisterTest {

        @Test
        @DisplayName("TC-R01: 用户名已存在 → 返回 error 30000")
        void testRegisterUserExists() {
            User user = new User();
            user.setUsername("existingUser");
            user.setPassword("123456");

            when(userService.select(anyMap(), anyMap())).thenReturn(mockQuery);
            when(mockQuery.getResultList()).thenReturn(Arrays.asList(new User()));

            Map<String, Object> result = userController.signUp(user);

            assertNotNull(result);
            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals(30000, error.get("code"));
            assertEquals("用户已存在", error.get("message"));
        }

        @Test
        @DisplayName("TC-R02: 新用户注册成功 → 返回 result=1")
        void testRegisterSuccess() {
            User user = new User();
            user.setUsername("newUser");
            user.setPassword("123456");

            when(userService.select(anyMap(), anyMap())).thenReturn(mockQuery);
            when(mockQuery.getResultList()).thenReturn(new ArrayList<>());
            when(userService.encryption(anyString())).thenReturn("encrypted_pwd");

            Map<String, Object> result = userController.signUp(user);

            assertNotNull(result);
            assertEquals(1, result.get("result"));
            verify(userService).save(user);
            assertNull(user.getUserId());
        }

        @Test
        @DisplayName("TC-R03: 注册时密码应被 MD5 加密")
        void testRegisterPasswordEncrypted() {
            User user = new User();
            user.setUsername("testUser");
            user.setPassword("rawPassword");

            when(userService.select(anyMap(), anyMap())).thenReturn(mockQuery);
            when(mockQuery.getResultList()).thenReturn(new ArrayList<>());
            when(userService.encryption("rawPassword")).thenReturn("md5_hashed");

            userController.signUp(user);

            verify(userService).encryption("rawPassword");
            assertEquals("md5_hashed", user.getPassword());
        }
    }

    // ==================== 登录 ====================

    @Nested
    @DisplayName("login - 用户登录")
    class LoginTest {

        private User mockUser;
        private UserGroup mockGroup;

        @BeforeEach
        void setup() {
            mockUser = new User();
            mockUser.setUserId(1);
            mockUser.setUsername("admin");
            mockUser.setPassword("21232f297a57a5a743894a0e4a801fc3");
            mockUser.setState(1);
            mockUser.setUserGroup("管理员");

            mockGroup = new UserGroup();
            mockGroup.setName("管理员");
            mockGroup.setSourceTable(null);
        }

        @Test
        @DisplayName("TC-L01: 用户名密码都为空 → 返回 error")
        void testEmptyCredentials() {
            Map<String, String> data = new HashMap<>();

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("error"));
        }

        @Test
        @DisplayName("TC-L02: 用户不存在 → 返回 用户不存在")
        void testUserNotFound() {
            Map<String, String> data = new HashMap<>();
            data.put("username", "nonexistent");
            data.put("password", "123456");

            when(userService.select(anyMap(), anyMap())).thenReturn(mockQuery);
            when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("用户不存在", error.get("message"));
        }

        @Test
        @DisplayName("TC-L03: 密码错误 → 返回 账号或密码不正确")
        void testWrongPassword() {
            Map<String, String> data = new HashMap<>();
            data.put("username", "admin");
            data.put("password", "wrongpwd");

            Query userQuery = mock(Query.class);
            Query groupQuery = mock(Query.class);

            when(userService.select(argThat(m -> m.containsKey("username")), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            when(userGroupService.select(argThat(m -> m.containsKey("name")), anyMap())).thenReturn(groupQuery);
            when(groupQuery.getResultList()).thenReturn(Arrays.asList(mockGroup));

            when(userService.encryption("wrongpwd")).thenReturn("wrong_hash");

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("账号或密码不正确", error.get("message"));
        }

        @Test
        @DisplayName("TC-L04: 账号被冻结(state!=1) → 返回 用户非可用状态")
        void testUserFrozen() {
            mockUser.setState(3);
            Map<String, String> data = new HashMap<>();
            data.put("username", "admin");
            data.put("password", "admin");

            Query userQuery = mock(Query.class);
            Query groupQuery = mock(Query.class);

            when(userService.select(argThat(m -> m.containsKey("username")), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            when(userGroupService.select(argThat(m -> m.containsKey("name")), anyMap())).thenReturn(groupQuery);
            when(groupQuery.getResultList()).thenReturn(Arrays.asList(mockGroup));

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("用户非可用状态，不能登录", error.get("message"));
        }

        @Test
        @DisplayName("TC-L05: 用户组不存在 → 返回 用户组不存在")
        void testUserGroupNotFound() {
            Map<String, String> data = new HashMap<>();
            data.put("username", "admin");
            data.put("password", "admin");

            Query userQuery = mock(Query.class);
            Query groupQuery = mock(Query.class);

            when(userService.select(argThat(m -> m.containsKey("username")), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            when(userGroupService.select(argThat(m -> m.containsKey("name")), anyMap())).thenReturn(groupQuery);
            when(groupQuery.getResultList()).thenReturn(new ArrayList<>());

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("用户组不存在", error.get("message"));
        }

        @Test
        @DisplayName("TC-L06: 审核未通过 → 返回 该用户审核未通过")
        void testExamineNotPassed() {
            mockGroup.setSourceTable("some_table");
            Map<String, String> data = new HashMap<>();
            data.put("username", "admin");
            data.put("password", "admin");

            Query userQuery = mock(Query.class);
            Query groupQuery = mock(Query.class);
            Query examineQuery = mock(Query.class);

            when(userService.select(argThat(m -> m.containsKey("username")), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            when(userGroupService.select(argThat(m -> m.containsKey("name")), anyMap())).thenReturn(groupQuery);
            when(groupQuery.getResultList()).thenReturn(Arrays.asList(mockGroup));

            when(userService.runCountSql(anyString())).thenReturn(examineQuery);
            when(examineQuery.getSingleResult()).thenReturn("审核中");

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("该用户审核未通过", error.get("message"));
        }

        @Test
        @DisplayName("TC-L07: 登录成功 → 返回 token 和用户信息")
        void testLoginSuccess() {
            Map<String, String> data = new HashMap<>();
            data.put("username", "admin");
            data.put("password", "admin");

            Query userQuery = mock(Query.class);
            Query groupQuery = mock(Query.class);

            when(userService.select(argThat(m -> m.containsKey("username")), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            when(userGroupService.select(argThat(m -> m.containsKey("name")), anyMap())).thenReturn(groupQuery);
            when(groupQuery.getResultList()).thenReturn(Arrays.asList(mockGroup));

            when(userService.encryption("admin")).thenReturn("21232f297a57a5a743894a0e4a801fc3");

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("result"));
            verify(tokenService).save(any(AccessToken.class));
        }

        @Test
        @DisplayName("TC-L08: 使用邮箱登录")
        void testLoginByEmail() {
            Map<String, String> data = new HashMap<>();
            data.put("email", "admin@test.com");
            data.put("password", "admin");

            Query userQuery = mock(Query.class);
            Query groupQuery = mock(Query.class);

            when(userService.select(argThat(m -> m.containsKey("email")), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            when(userGroupService.select(anyMap(), anyMap())).thenReturn(groupQuery);
            when(groupQuery.getResultList()).thenReturn(Arrays.asList(mockGroup));

            when(userService.encryption("admin")).thenReturn("21232f297a57a5a743894a0e4a801fc3");

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("result"));
        }

        @Test
        @DisplayName("TC-L09: 使用手机号登录")
        void testLoginByPhone() {
            Map<String, String> data = new HashMap<>();
            data.put("phone", "13800138000");
            data.put("password", "admin");

            Query userQuery = mock(Query.class);
            Query groupQuery = mock(Query.class);

            when(userService.select(argThat(m -> m.containsKey("phone")), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            when(userGroupService.select(anyMap(), anyMap())).thenReturn(groupQuery);
            when(groupQuery.getResultList()).thenReturn(Arrays.asList(mockGroup));

            when(userService.encryption("admin")).thenReturn("21232f297a57a5a743894a0e4a801fc3");

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("result"));
        }

        @Test
        @DisplayName("TC-L10: password 为 null → 返回 error")
        void testNullPassword() {
            Map<String, String> data = new HashMap<>();
            data.put("username", "admin");
            // password is null

            Query userQuery = mock(Query.class);
            when(userService.select(anyMap(), anyMap())).thenReturn(userQuery);
            when(userQuery.getResultList()).thenReturn(Arrays.asList(mockUser));

            Map<String, Object> result = userController.login(data, request);

            assertTrue(result.containsKey("error"));
        }
    }

    // ==================== 找回密码 ====================

    @Nested
    @DisplayName("forgetPassword - 找回密码")
    class ForgetPasswordTest {

        @Test
        @DisplayName("TC-F01: 验证码为空 → error")
        void testEmptyCode() {
            User form = new User();
            form.setUsername("admin");
            form.setPassword("newPwd");
            form.setCode("");

            Map<String, Object> result = userController.forgetPassword(form, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("验证码不能为空", error.get("message"));
        }

        @Test
        @DisplayName("TC-F02: 用户名为空 → error")
        void testEmptyUsername() {
            User form = new User();
            form.setUsername("");
            form.setPassword("newPwd");
            form.setCode("1234");

            Map<String, Object> result = userController.forgetPassword(form, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("用户名不能为空", error.get("message"));
        }

        @Test
        @DisplayName("TC-F03: 新密码为空 → error")
        void testEmptyPassword() {
            User form = new User();
            form.setUsername("admin");
            form.setPassword("");
            form.setCode("1234");

            Map<String, Object> result = userController.forgetPassword(form, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("密码不能为空", error.get("message"));
        }

        @Test
        @DisplayName("TC-F04: 用户不存在 → error 70000")
        void testUserNotExists() {
            User form = new User();
            form.setUsername("nonexistent");
            form.setPassword("newPwd");
            form.setCode("1234");

            when(userService.select(anyMap(), anyMap())).thenReturn(mockQuery);
            when(userService.readConfig(request)).thenReturn(new HashMap<>());
            when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

            Map<String, Object> result = userController.forgetPassword(form, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals(70000, error.get("code"));
            assertEquals("用户不存在", error.get("message"));
        }

        @Test
        @DisplayName("TC-F05: 找回密码成功")
        void testForgetPasswordSuccess() {
            User form = new User();
            form.setUsername("admin");
            form.setPassword("newPassword");
            form.setCode("1234");

            User existingUser = new User();
            existingUser.setUserId(1);
            existingUser.setUsername("admin");

            when(userService.select(anyMap(), anyMap())).thenReturn(mockQuery);
            when(userService.readConfig(request)).thenReturn(new HashMap<>());
            when(mockQuery.getResultList()).thenReturn(Arrays.asList(existingUser));
            when(userService.encryption("newPassword")).thenReturn("new_md5_hash");

            Map<String, Object> result = userController.forgetPassword(form, request);

            assertEquals(1, result.get("result"));
            verify(userService).update(anyMap(), anyMap(), any());
        }
    }

    // ==================== 修改密码 ====================

    @Nested
    @DisplayName("change_password - 修改密码")
    class ChangePasswordTest {

        @Test
        @DisplayName("TC-CP01: 旧密码错误 → error")
        void testOldPasswordWrong() {
            when(request.getHeader("x-auth-token")).thenReturn("valid_token");

            AccessToken token = new AccessToken();
            token.setUser_id(1);
            when(tokenService.findOne(anyMap())).thenReturn(token);
            when(userService.encryption("wrongOld")).thenReturn("wrong_hash");
            when(userService.readConfig(request)).thenReturn(new HashMap<>());
            when(userService.count(anyMap(), anyMap())).thenReturn(mockQuery);
            when(mockQuery.getResultList()).thenReturn(Arrays.asList(0L));

            Map<String, String> data = new HashMap<>();
            data.put("o_password", "wrongOld");
            data.put("password", "newPwd");

            Map<String, Object> result = userController.change_password(data, request);

            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            Map<String, Object> error = (Map<String, Object>) result.get("error");
            assertEquals("密码修改失败！", error.get("message"));
        }

        @Test
        @DisplayName("TC-CP02: 旧密码正确 → 修改成功")
        void testChangePasswordSuccess() {
            when(request.getHeader("x-auth-token")).thenReturn("valid_token");

            AccessToken token = new AccessToken();
            token.setUser_id(1);
            when(tokenService.findOne(anyMap())).thenReturn(token);
            when(userService.encryption("oldPwd")).thenReturn("old_hash");
            when(userService.encryption("newPwd")).thenReturn("new_hash");
            when(userService.readConfig(request)).thenReturn(new HashMap<>());
            when(userService.count(anyMap(), anyMap())).thenReturn(mockQuery);
            when(mockQuery.getResultList()).thenReturn(Arrays.asList(1L));

            Map<String, String> data = new HashMap<>();
            data.put("o_password", "oldPwd");
            data.put("password", "newPwd");

            Map<String, Object> result = userController.change_password(data, request);

            assertEquals(1, result.get("result"));
            verify(userService).update(anyMap(), anyMap(), any());
        }
    }

    // ==================== 登录态 / 退出 ====================

    @Nested
    @DisplayName("state & quit - 登录态与退出")
    class StateAndQuitTest {

        @Test
        @DisplayName("TC-S01: Token 为空 → 用户未登录")
        void testStateNoToken() {
            when(request.getHeader("x-auth-token")).thenReturn(null);

            Map<String, Object> result = userController.state(request);

            assertTrue(result.containsKey("error"));
        }

        @Test
        @DisplayName("TC-S02: Token 有效 → 返回用户信息")
        void testStateValid() {
            when(request.getHeader("x-auth-token")).thenReturn("valid_token");

            AccessToken token = new AccessToken();
            token.setUser_id(1);
            when(tokenService.findOne(anyMap())).thenReturn(token);

            User user = new User();
            user.setUserId(1);
            user.setUsername("admin");

            when(userService.readConfig(request)).thenReturn(new HashMap<>());
            when(userService.select(anyMap(), anyMap())).thenReturn(mockQuery);
            when(mockQuery.getResultList()).thenReturn(Arrays.asList(user));

            Map<String, Object> result = userController.state(request);

            assertTrue(result.containsKey("result"));
        }

        @Test
        @DisplayName("TC-S03: Token 无效（找不到对应用户） → 用户未登录")
        void testStateInvalidToken() {
            when(request.getHeader("x-auth-token")).thenReturn("invalid_token");
            when(tokenService.findOne(anyMap())).thenReturn(null);

            Map<String, Object> result = userController.state(request);

            assertTrue(result.containsKey("error"));
        }

        @Test
        @DisplayName("TC-Q01: 退出登录 → 返回成功")
        void testQuit() {
            when(request.getHeader("x-auth-token")).thenReturn("valid_token");
            when(userService.readConfig(request)).thenReturn(new HashMap<>());

            Map<String, Object> result = userController.quit(request);

            assertNotNull(result);
            assertEquals("退出登录成功！", result.get("result"));
        }

        @Test
        @DisplayName("TC-Q02: 退出时 token 删除异常仍返回成功（容错）")
        void testQuitWithException() {
            when(request.getHeader("x-auth-token")).thenReturn("expired_token");
            when(userService.readConfig(request)).thenReturn(new HashMap<>());
            doThrow(new RuntimeException("token not found")).when(tokenService).delete(anyMap(), anyMap());

            Map<String, Object> result = userController.quit(request);

            assertEquals("退出登录成功！", result.get("result"));
        }
    }

    // ==================== tokenGetUserId ====================

    @Nested
    @DisplayName("tokenGetUserId - Token 解析")
    class TokenGetUserIdTest {

        @Test
        @DisplayName("TC-T01: token 为 null → 返回 0")
        void testNullToken() {
            assertEquals(0, userController.tokenGetUserId(null));
        }

        @Test
        @DisplayName("TC-T02: token 为空字符串 → 返回 0")
        void testEmptyToken() {
            assertEquals(0, userController.tokenGetUserId(""));
        }

        @Test
        @DisplayName("TC-T03: token 有效 → 返回 userId")
        void testValidToken() {
            AccessToken accessToken = new AccessToken();
            accessToken.setUser_id(42);
            when(tokenService.findOne(anyMap())).thenReturn(accessToken);

            assertEquals(42, userController.tokenGetUserId("valid_token_string"));
        }

        @Test
        @DisplayName("TC-T04: token 查不到记录 → 返回 0")
        void testTokenNotFound() {
            when(tokenService.findOne(anyMap())).thenReturn(null);

            assertEquals(0, userController.tokenGetUserId("nonexistent_token"));
        }
    }
}

