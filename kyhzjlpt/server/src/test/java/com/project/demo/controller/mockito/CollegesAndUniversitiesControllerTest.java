package com.project.demo.controller.mockito;

import com.project.demo.controller.CollegesAndUniversitiesController;
import com.project.demo.service.CollegesAndUniversitiesService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * CollegesAndUniversitiesController 单元测试
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CollegesAndUniversitiesController 单元测试")
public class CollegesAndUniversitiesControllerTest {

    @Mock
    private CollegesAndUniversitiesService collegesService;

    @InjectMocks
    private CollegesAndUniversitiesController controller;

    @Mock
    private HttpServletRequest request;

    @Test
    @DisplayName("add() 正确读取 body 并调用 addMap → success(1)")
    void testAddSuccess() throws IOException {
        String json = "{\"name_of_institution\":\"清华大学\"}";
        BufferedReader reader = new BufferedReader(new StringReader(json));
        when(request.getReader()).thenReturn(reader);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("name_of_institution", "清华大学");
        when(collegesService.readBody(any())).thenReturn(body);

        Map<String, Object> result = controller.add(request);
        assertEquals(1, result.get("result"));
    }
}
