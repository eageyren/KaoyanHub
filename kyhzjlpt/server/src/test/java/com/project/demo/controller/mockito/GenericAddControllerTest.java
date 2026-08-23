package com.project.demo.controller.mockito;

import com.project.demo.controller.DataSharingController;
import com.project.demo.controller.DataTypeController;
import com.project.demo.controller.OnlineQuestionsController;
import com.project.demo.controller.PostgraduateExaminationMaterialsController;
import com.project.demo.service.DataSharingService;
import com.project.demo.service.DataTypeService;
import com.project.demo.service.OnlineQuestionsService;
import com.project.demo.service.PostgraduateExaminationMaterialsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 批量测试 4 个具有相同 add() 模式的控制器:
 * DataSharingController, DataTypeController,
 * OnlineQuestionsController, PostgraduateExaminationMaterialsController
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("通用 add() 控制器单元测试")
public class GenericAddControllerTest {

    // ===== DataSharingController =====

    @Mock
    private DataSharingService dataSharingService;

    @Mock
    private DataTypeService dataTypeService;

    @Mock
    private OnlineQuestionsService onlineQuestionsService;

    @Mock
    private PostgraduateExaminationMaterialsService materialsService;

    @Mock
    private HttpServletRequest request;

    @Test
    @DisplayName("DataSharingController.add() → success(1)")
    void testDataSharingAdd() throws IOException {
        DataSharingController ctrl = new DataSharingController(dataSharingService);
        String json = "{\"data_name\":\"测试\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("data_name", "测试");
        when(dataSharingService.readBody(any())).thenReturn(body);
        assertEquals(1, ctrl.add(request).get("result"));
    }

    @Test
    @DisplayName("DataTypeController.add() → success(1)")
    void testDataTypeAdd() throws IOException {
        DataTypeController ctrl = new DataTypeController(dataTypeService);
        String json = "{\"data_type\":\"真题\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("data_type", "真题");
        when(dataTypeService.readBody(any())).thenReturn(body);
        assertEquals(1, ctrl.add(request).get("result"));
    }

    @Test
    @DisplayName("OnlineQuestionsController.add() → success(1)")
    void testOnlineQuestionsAdd() throws IOException {
        OnlineQuestionsController ctrl = new OnlineQuestionsController(onlineQuestionsService);
        String json = "{\"problem_description\":\"问题\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("problem_description", "问题");
        when(onlineQuestionsService.readBody(any())).thenReturn(body);
        assertEquals(1, ctrl.add(request).get("result"));
    }

    @Test
    @DisplayName("PostgraduateExaminationMaterialsController.add() → success(1)")
    void testMaterialsAdd() throws IOException {
        PostgraduateExaminationMaterialsController ctrl =
                new PostgraduateExaminationMaterialsController(materialsService);
        String json = "{\"data_name\":\"资料\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("data_name", "资料");
        when(materialsService.readBody(any())).thenReturn(body);
        assertEquals(1, ctrl.add(request).get("result"));
    }
}
