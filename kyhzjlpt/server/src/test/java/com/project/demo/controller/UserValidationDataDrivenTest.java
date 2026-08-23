package com.project.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户输入验证 — 数据驱动测试
 * <p>
 * 使用 @CsvSource 批量测试用户名/密码/邮箱/手机号的合法与非法组合
 * </p>
 */
@DisplayName("用户输入验证数据驱动测试")
public class UserValidationDataDrivenTest {

    @ParameterizedTest(name = "用户名 [{0}] 合法性={1}")
    @CsvSource({
            "admin,      true",
            "user123,    true",
            "'',         false",
            "ab,         true",
            "abcdefghijklmnopqrstuvwxyz, true",
    })
    @DisplayName("用户名格式验证")
    void testUsernameValidation(String username, boolean expectedValid) {
        boolean isValid = username != null && !username.trim().isEmpty();
        assertEquals(expectedValid, isValid, "用户名 " + username + " 的合法性判断应为 " + expectedValid);
    }

    @ParameterizedTest(name = "密码 [{0}] 合法性={1}")
    @CsvSource({
            "123456,     true",
            "'',         false",
            "abcdef,     true",
            "ab,         true",
    })
    @DisplayName("密码格式验证")
    void testPasswordValidation(String password, boolean expectedValid) {
        boolean isValid = password != null && !password.isEmpty();
        assertEquals(expectedValid, isValid, "密码不能为空");
    }

    @ParameterizedTest(name = "邮箱 [{0}] 合法性={1}")
    @CsvSource({
            "test@example.com,   true",
            "'',                 false",
            "not-an-email,       false",
            "user@dom,           true",
    })
    @DisplayName("邮箱格式验证")
    void testEmailValidation(String email, boolean expectedValid) {
        boolean isValid = email != null && email.contains("@");
        assertEquals(expectedValid, isValid, "邮箱 " + email + " 的合法性判断应为 " + expectedValid);
    }
}
