package com.project.demo.service;

import com.project.demo.entity.CollegesAndUniversities;
import com.project.demo.service.base.BaseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 资料模块 Service 测试
 * 覆盖: PostgraduateExaminationMaterialsService, DataSharingService, DataTypeService,
 *       CollegesAndUniversitiesService
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
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

    // ===== 真实数据 CRUD 操作测试 =====

    @Test
    @Transactional
    @DisplayName("CRUD-资料: insert → select 验证")
    void testInsertMaterial() {
        Map<String, Object> body = new HashMap<>();
        body.put("data_name", "测试考研资料");
        body.put("data_type", "真题");
        body.put("knowledge_points", "高等数学");
        materialsService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("data_name", "测试考研资料");
        List list = materialsService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        materialsService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-资料: update → 验证更新")
    void testUpdateMaterial() {
        Map<String, Object> body = new HashMap<>();
        body.put("data_name", "待更新资料");
        body.put("data_type", "笔记");
        materialsService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("data_name", "待更新资料");
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("data_name", "已更新资料");
        materialsService.update(query, new HashMap<>(), updateBody);

        Map<String, String> newQuery = new HashMap<>();
        newQuery.put("data_name", "已更新资料");
        assertFalse(materialsService.select(newQuery, new HashMap<>()).getResultList().isEmpty());

        materialsService.delete(newQuery, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-共享: insert → 查询验证")
    void testInsertSharing() {
        Map<String, Object> body = new HashMap<>();
        body.put("data_name", "共享测试数据");
        body.put("data_type", "课件");
        dataSharingService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("data_name", "共享测试数据");
        List list = dataSharingService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        dataSharingService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-院校: insert → 查询验证")
    void testInsertCollege() {
        Map<String, Object> body = new HashMap<>();
        body.put("name_of_institution", "测试大学");
        body.put("hits", 0);
        body.put("praise_len", 0);
        collegesService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("name_of_institution", "测试大学");
        List list = collegesService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        collegesService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-院校: 模糊搜索院校名称")
    void testSearchCollege() {
        Map<String, Object> body = new HashMap<>();
        body.put("name_of_institution", "北京大学测试");
        collegesService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("name_of_institution", "北京");
        Map<String, String> config = new HashMap<>();
        config.put("like", "0");
        List list = collegesService.select(query, config).getResultList();
        assertFalse(list.isEmpty());

        Map<String, String> delQuery = new HashMap<>();
        delQuery.put("name_of_institution", "北京大学测试");
        collegesService.delete(delQuery, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-院校: count 院校数量")
    void testCountColleges() {
        Map<String, Object> body = new HashMap<>();
        body.put("name_of_institution", "计数测试院校");
        collegesService.insert(body);

        Query countQuery = collegesService.count(new HashMap<>(), new HashMap<>());
        assertTrue(((Number) countQuery.getSingleResult()).longValue() >= 1);

        Map<String, String> delQuery = new HashMap<>();
        delQuery.put("name_of_institution", "计数测试院校");
        collegesService.delete(delQuery, new HashMap<>());
    }
}
