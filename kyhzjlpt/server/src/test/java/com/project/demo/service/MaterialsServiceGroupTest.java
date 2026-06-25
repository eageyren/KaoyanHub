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
 * 资料模块 Service 测试
 * 覆盖: PostgraduateExaminationMaterialsService, DataSharingService, DataTypeService,
 *       CollegesAndUniversitiesService
 */
@SpringBootTest
@DisplayName("资料与院校模块 Service 测试")
public class MaterialsServiceGroupTest {

    @Autowired
    private PostgraduateExaminationMaterialsService materialsService;

    @Autowired
    private DataSharingService dataSharingService;

    @Autowired
    private DataTypeService dataTypeService;

    @Autowired
    private CollegesAndUniversitiesService collegesService;

    // ===== Spring 注入验证 =====

    @Nested
    @DisplayName("Spring 容器注入验证")
    class InjectionTest {

        @Test
        @DisplayName("PostgraduateExaminationMaterialsService 注入成功")
        void materialsServiceNotNull() {
            assertNotNull(materialsService);
        }

        @Test
        @DisplayName("DataSharingService 注入成功")
        void dataSharingServiceNotNull() {
            assertNotNull(dataSharingService);
        }

        @Test
        @DisplayName("DataTypeService 注入成功")
        void dataTypeServiceNotNull() {
            assertNotNull(dataTypeService);
        }

        @Test
        @DisplayName("CollegesAndUniversitiesService 注入成功")
        void collegesServiceNotNull() {
            assertNotNull(collegesService);
        }
    }

    // ===== humpToLine 资料模块字段验证 =====

    @Nested
    @DisplayName("humpToLine - 资料模块字段名转换")
    class HumpToLineTest {

        @Test
        @DisplayName("postgraduateExaminationMaterials → postgraduate_examination_materials")
        void testMaterialsEntity() {
            assertEquals("postgraduate_examination_materials",
                    BaseService.humpToLine("PostgraduateExaminationMaterials"));
        }

        @Test
        @DisplayName("dataType → data_type")
        void testDataType() {
            assertEquals("data_type", BaseService.humpToLine("dataType"));
        }

        @Test
        @DisplayName("collegesAndUniversities → colleges_and_universities")
        void testColleges() {
            assertEquals("colleges_and_universities", BaseService.humpToLine("CollegesAndUniversities"));
        }

        @Test
        @DisplayName("uploadTime → upload_time")
        void testUploadTime() {
            assertEquals("upload_time", BaseService.humpToLine("uploadTime"));
        }

        @Test
        @DisplayName("fileUrl → file_url")
        void testFileUrl() {
            assertEquals("file_url", BaseService.humpToLine("fileUrl"));
        }
    }

    // ===== toWhereSql 资料模块场景 =====

    @Nested
    @DisplayName("toWhereSql - 资料模块查询场景")
    class ToWhereSqlTest {

        @Test
        @DisplayName("按院校名称模糊搜索")
        void testCollegeNameLike() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("name", "北京");
            String sql = collegesService.toWhereSql(q, true, null);
            assertTrue(sql.contains("LIKE '%北京%'"));
        }

        @Test
        @DisplayName("按资料类型精确匹配")
        void testDataTypeExact() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("dataType", "真题");
            String sql = materialsService.toWhereSql(q, false, null);
            assertTrue(sql.contains("data_type"));
            assertTrue(sql.contains("= '真题'"));
        }

        @Test
        @DisplayName("按用户ID查询共享数据")
        void testDataSharingByUserId() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("userId", "10");
            String sql = dataSharingService.toWhereSql(q, false, null);
            assertTrue(sql.contains("user_id"));
            assertTrue(sql.contains("= '10'"));
        }

        @Test
        @DisplayName("sqlwhere 附加条件生效")
        void testSqlWhereAppended() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("dataType", "笔记");
            String sql = materialsService.toWhereSql(q, false, "status = 1");
            assertTrue(sql.contains("status = 1"));
        }

        @Test
        @DisplayName("空条件返回空字符串")
        void testEmpty() {
            assertEquals("", materialsService.toWhereSql(new HashMap<>(), false, null));
        }
    }
}
