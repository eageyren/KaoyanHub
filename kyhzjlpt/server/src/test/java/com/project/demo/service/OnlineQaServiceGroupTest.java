package com.project.demo.service;

import com.project.demo.entity.OnlineQa;
import com.project.demo.entity.OnlineQuestions;
import com.project.demo.service.base.BaseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 在线问答模块 Service 测试
 * 覆盖: OnlineQaService, OnlineQuestionsService
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("在线问答模块 Service 测试")
public class OnlineQaServiceGroupTest {

    @Autowired
    private OnlineQaService onlineQaService;

    @Autowired
    private OnlineQuestionsService onlineQuestionsService;

    // ===== Spring 注入验证 =====

    @Nested
    @DisplayName("Spring 容器注入验证")
    class InjectionTest {

        @Test
        @DisplayName("OnlineQaService 注入成功")
        void onlineQaServiceNotNull() {
            assertNotNull(onlineQaService);
        }

        @Test
        @DisplayName("OnlineQuestionsService 注入成功")
        void onlineQuestionsServiceNotNull() {
            assertNotNull(onlineQuestionsService);
        }
    }

    // ===== humpToLine 问答模块字段验证 =====

    @Nested
    @DisplayName("humpToLine - 问答字段名转换")
    class HumpToLineTest {

        @Test
        @DisplayName("onlineQaId → online_qa_id")
        void testOnlineQaId() {
            assertEquals("online_qa_id", BaseService.humpToLine("onlineQaId"));
        }

        @Test
        @DisplayName("questionContent → question_content")
        void testQuestionContent() {
            assertEquals("question_content", BaseService.humpToLine("questionContent"));
        }

        @Test
        @DisplayName("answerContent → answer_content")
        void testAnswerContent() {
            assertEquals("answer_content", BaseService.humpToLine("answerContent"));
        }

        @Test
        @DisplayName("adoptState → adopt_state")
        void testAdoptState() {
            assertEquals("adopt_state", BaseService.humpToLine("adoptState"));
        }
    }

    // ===== toWhereSql 问答场景条件拼接 =====

    @Nested
    @DisplayName("toWhereSql - 问答场景条件拼接")
    class ToWhereSqlTest {

        @Test
        @DisplayName("按 userId 查询问题")
        void testByUserId() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("userId", "3");
            String sql = onlineQuestionsService.toWhereSql(q, false, null);
            assertTrue(sql.contains("user_id"));
            assertTrue(sql.contains("= '3'"));
        }

        @Test
        @DisplayName("按问题内容模糊搜索")
        void testContentLike() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("questionContent", "数学");
            String sql = onlineQuestionsService.toWhereSql(q, true, null);
            assertTrue(sql.contains("LIKE '%数学%'"));
        }

        @Test
        @DisplayName("按回答的 adoptState 精确查询")
        void testAdoptState() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("adoptState", "1");
            String sql = onlineQaService.toWhereSql(q, false, null);
            assertTrue(sql.contains("adopt_state"));
            assertTrue(sql.contains("= '1'"));
        }

        @Test
        @DisplayName("空条件返回空字符串")
        void testEmpty() {
            assertEquals("", onlineQaService.toWhereSql(new HashMap<>(), false, null));
        }

        @Test
        @DisplayName("精确匹配不含 LIKE 关键字")
        void testNoLikeInExact() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("userId", "1");
            String sql = onlineQaService.toWhereSql(q, false, null);
            assertFalse(sql.contains("LIKE"));
        }
    }

    // ===== encryption 在问答模块的验证 =====

    @Nested
    @DisplayName("encryption - 加密方法验证")
    class EncryptionTest {

        @Test
        @DisplayName("onlineQaService.encryption 返回 32 位 MD5")
        void testFormat() {
            String result = onlineQaService.encryption("qaTest");
            assertEquals(32, result.length());
            assertTrue(result.matches("[0-9a-f]{32}"));
        }

        @Test
        @DisplayName("两个 Service 的 encryption 结果一致")
        void testConsistency() {
            String input = "same_input";
            assertEquals(
                    onlineQaService.encryption(input),
                    onlineQuestionsService.encryption(input)
            );
        }
    }

    // ===== 真实数据 CRUD 操作测试 =====

    @Test
    @Transactional
    @DisplayName("CRUD-提问: insert → select 验证")
    void testInsertQuestion() {
        Map<String, Object> body = new HashMap<>();
        body.put("question_no", "TEST-Q-001");
        body.put("ask_the_user", 1);
        body.put("problem_description", "这是一个测试问题");
        body.put("examine_state", "待审核");
        onlineQuestionsService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("question_no", "TEST-Q-001");
        List list = onlineQuestionsService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        onlineQuestionsService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-提问: update 审核状态 → 验证更新")
    void testUpdateExamineState() {
        Map<String, Object> body = new HashMap<>();
        body.put("question_no", "TEST-Q-002");
        body.put("ask_the_user", 2);
        body.put("examine_state", "待审核");
        onlineQuestionsService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("question_no", "TEST-Q-002");
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("examine_state", "已通过");
        onlineQuestionsService.update(query, new HashMap<>(), updateBody);

        OnlineQuestions q = onlineQuestionsService.findOne(query);
        assertNotNull(q);

        onlineQuestionsService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-提问: delete → 验证删除")
    void testDeleteQuestion() {
        Map<String, Object> body = new HashMap<>();
        body.put("question_no", "TEST-Q-003");
        body.put("ask_the_user", 3);
        onlineQuestionsService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("question_no", "TEST-Q-003");
        onlineQuestionsService.delete(query, new HashMap<>());

        assertNull(onlineQuestionsService.findOne(query));
    }

    @Test
    @Transactional
    @DisplayName("CRUD-答疑: insert → 查询验证")
    void testInsertQa() {
        Map<String, Object> body = new HashMap<>();
        body.put("question_no", "TEST-QA-001");
        body.put("ask_the_user", 4);
        body.put("qa_description", "这是答疑内容");
        onlineQaService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("question_no", "TEST-QA-001");
        List list = onlineQaService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        onlineQaService.delete(query, new HashMap<>());
    }
}
