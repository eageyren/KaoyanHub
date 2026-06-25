package com.project.demo.interceptor;

import com.project.demo.interceptor.LoginInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * LoginInterceptor 白盒测试 —— 覆盖拦截器的所有分支路径
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("LoginInterceptor 白盒测试")
public class LoginInterceptorTest {

    private LoginInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setup() {
        interceptor = new LoginInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    @DisplayName("TC-LI01: 登录接口放行 → 返回 true")
    void testLoginUrlAllowed() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/user/login"));
        when(request.getMethod()).thenReturn("POST");
        when(request.getHeader("x-auth-token")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());
        assertTrue(result);
    }

    @Test
    @DisplayName("TC-LI02: 注册接口放行 → 返回 true")
    void testRegisterUrlAllowed() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/user/register"));
        when(request.getMethod()).thenReturn("POST");
        when(request.getHeader("x-auth-token")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());
        assertTrue(result);
    }

    @Test
    @DisplayName("TC-LI03: 登录态查询接口放行 → 返回 true")
    void testStateUrlAllowed() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/user/state"));
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("x-auth-token")).thenReturn("some_token");

        boolean result = interceptor.preHandle(request, response, new Object());
        assertTrue(result);
    }

    @Test
    @DisplayName("TC-LI04: 其他接口无 Token（当前实现放行 — 安全漏洞确认）")
    void testOtherUrlNoToken() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/forum/add"));
        when(request.getMethod()).thenReturn("POST");
        when(request.getHeader("x-auth-token")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());
        // 当前实现：Token 校验被注释，所有请求都放行
        assertTrue(result, "当前 Token 校验被注释，所有接口均放行（安全漏洞）");
    }

    @Test
    @DisplayName("TC-LI05: 其他接口有 Token（放行）")
    void testOtherUrlWithToken() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/exam/get_list"));
        when(request.getMethod()).thenReturn("GET");
        when(request.getHeader("x-auth-token")).thenReturn("valid_token");

        boolean result = interceptor.preHandle(request, response, new Object());
        assertTrue(result);
    }

    @Test
    @DisplayName("TC-LI06: OPTIONS 预检请求放行")
    void testOptionsRequest() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/forum/get_list"));
        when(request.getMethod()).thenReturn("OPTIONS");
        when(request.getHeader("x-auth-token")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());
        assertTrue(result);
    }

    @Test
    @DisplayName("TC-LI07: 验证跨域头设置")
    void testCorsHeaders() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/user/login"));
        when(request.getMethod()).thenReturn("POST");
        when(request.getHeader("x-auth-token")).thenReturn(null);
        when(request.getHeader("Origin")).thenReturn("http://localhost:8080");
        when(request.getHeader("Access-Control-Request-Headers")).thenReturn("x-auth-token,content-type");

        interceptor.preHandle(request, response, new Object());

        verify(response).setHeader("Access-control-Allow-Origin", "http://localhost:8080");
        verify(response).setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        verify(response).setHeader("Access-Control-Allow-Credentials", "true");
        verify(response).setHeader("Access-Control-Allow-Headers", "x-auth-token,content-type");
    }

    @Test
    @DisplayName("TC-LI08: Origin 为 null 时不设置 Allow-Origin")
    void testNullOrigin() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/user/login"));
        when(request.getMethod()).thenReturn("POST");
        when(request.getHeader("x-auth-token")).thenReturn(null);
        when(request.getHeader("Origin")).thenReturn(null);

        interceptor.preHandle(request, response, new Object());

        verify(response, never()).setHeader(eq("Access-control-Allow-Origin"), anyString());
    }

    @Test
    @DisplayName("TC-LI09: DELETE 请求无 Token 也放行（安全漏洞确认）")
    void testDeleteWithoutToken() throws Exception {
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:5000/api/forum/del"));
        when(request.getMethod()).thenReturn("DELETE");
        when(request.getHeader("x-auth-token")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());
        assertTrue(result, "DELETE 请求无 Token 也放行 — Token 校验被注释");
    }
}

