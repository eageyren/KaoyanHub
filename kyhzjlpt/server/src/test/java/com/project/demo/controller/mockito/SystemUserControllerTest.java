package com.project.demo.controller.mockito;

import com.project.demo.controller.SystemUserController;
import com.project.demo.service.SystemUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * SystemUserController 单元测试 — 验证自定义 add() 方法
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SystemUserController 单元测试")
public class SystemUserControllerTest {

    @Mock
    private SystemUserService systemUserService;

    @InjectMocks
    private SystemUserController systemUserController;

    @Mock
    private HttpServletRequest request;

    @Test
    @DisplayName("add() 正确读取 body 并调用 addMap → success(1)")
    void testAddSuccess() throws IOException {
        String json = "{\"user_name\":\"admin\"}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("user_name", "admin");
        when(systemUserService.readBody(any())).thenReturn(body);

        Map<String, Object> result = systemUserController.add(request);

        assertEquals(1, result.get("result"));
    }

    @Test
    @DisplayName("@RequestMapping 注解值为 /system_user")
    void testRequestMapping() {
        RequestMapping annotation = systemUserController.getClass().getAnnotation(RequestMapping.class);
        assertNotNull(annotation);
        assertArrayEquals(new String[]{"/system_user"}, annotation.value());
    }
}
