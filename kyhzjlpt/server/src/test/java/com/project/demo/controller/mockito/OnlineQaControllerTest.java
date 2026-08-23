package com.project.demo.controller.mockito;

import com.project.demo.controller.OnlineQaController;
import com.project.demo.service.OnlineQaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * OnlineQaController 白盒测试 —— 验证答疑编号唯一性校验逻辑
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("OnlineQaController 白盒测试")
public class OnlineQaControllerTest {

    @Mock
    private OnlineQaService onlineQaService;

    @InjectMocks
    private OnlineQaController onlineQaController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Query mockQuery;

    @Test
    @DisplayName("TC-OA01: question_no 重复 → 返回 error 30000")
    void testDuplicateQuestionNo() throws IOException {
        String json = "{\"question_no\":\"Q001\",\"problem_description\":\"测试问题\"}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("question_no", "Q001");
        body.put("problem_description", "测试问题");
        when(onlineQaService.readBody(any())).thenReturn(body);

        when(onlineQaService.select(argThat(m -> m.containsKey("question_no")), anyMap())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(Arrays.asList(new Object()));

        Map<String, Object> result = onlineQaController.add(request);

        assertTrue(result.containsKey("error"));
        @SuppressWarnings("unchecked")
        Map<String, Object> error = (Map<String, Object>) result.get("error");
        assertEquals(30000, error.get("code"));
        assertEquals("字段问题编号内容不能重复", error.get("message"));
    }

    @Test
    @DisplayName("TC-OA02: question_no 不重复 → 添加成功")
    void testAddSuccess() throws IOException {
        String json = "{\"question_no\":\"Q002\",\"problem_description\":\"新问题\"}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("question_no", "Q002");
        body.put("problem_description", "新问题");
        when(onlineQaService.readBody(any())).thenReturn(body);

        when(onlineQaService.select(argThat(m -> m.containsKey("question_no")), anyMap())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        Map<String, Object> result = onlineQaController.add(request);

        assertEquals(1, result.get("result"));
    }

    @Test
    @DisplayName("TC-OA03: question_no 为 null → 仍尝试查询（边界测试）")
    void testNullQuestionNo() throws IOException {
        String json = "{\"question_no\":null,\"problem_description\":\"问题\"}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("question_no", null);
        body.put("problem_description", "问题");
        when(onlineQaService.readBody(any())).thenReturn(body);

        when(onlineQaService.select(anyMap(), anyMap())).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(new ArrayList<>());

        Map<String, Object> result = onlineQaController.add(request);
        assertEquals(1, result.get("result"));
    }
}

