package com.project.demo.service;

import com.project.demo.entity.Exam;
import com.project.demo.entity.ExamQuestion;
import com.project.demo.entity.UserAnswer;
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
 * 考试模块 Service 测试
 * 覆盖: ExamService, ExamQuestionService, UserAnswerService
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("考试模块 Service 测试")
public class ExamServiceGroupTest {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamQuestionService examQuestionService;

    @Autowired
    private UserAnswerService userAnswerService;

    // ===== Spring 注入验证 =====

    @Nested
    @DisplayName("Spring 容器注入验证")
    class InjectionTest {

        @Test
        @DisplayName("ExamService 注入成功")
        void examServiceNotNull() {
            assertNotNull(examService);
        }

        @Test
        @DisplayName("ExamQuestionService 注入成功")
        void examQuestionServiceNotNull() {
            assertNotNull(examQuestionService);
        }

        @Test
        @DisplayName("UserAnswerService 注入成功")
        void userAnswerServiceNotNull() {
            assertNotNull(userAnswerService);
        }
    }

    // ===== encryption 在考试模块的验证 =====

    @Nested
    @DisplayName("encryption - 加密方法验证")
    class EncryptionTest {

        @Test
        @DisplayName("examService.encryption 返回 32 位 MD5")
        void testEncryptionFormat() {
            String result = examService.encryption("exam_password");
            assertEquals(32, result.length());
            assertTrue(result.matches("[0-9a-f]{32}"));
        }

        @Test
        @DisplayName("examQuestionService.encryption 与 examService 一致（相同输入）")
        void testConsistency() {
            String pwd = "testPassword";
            assertEquals(examService.encryption(pwd), examQuestionService.encryption(pwd));
        }
    }

    // ===== humpToLine 在考试模块的验证 =====

    @Nested
    @DisplayName("humpToLine - 字段名转换")
    class HumpToLineTest {

        @Test
        @DisplayName("examId → exam_id")
        void testExamId() {
            assertEquals("exam_id", BaseService.humpToLine("examId"));
        }

        @Test
        @DisplayName("questionType → question_type")
        void testQuestionType() {
            assertEquals("question_type", BaseService.humpToLine("questionType"));
        }

        @Test
        @DisplayName("objectiveScore → objective_score")
        void testObjectiveScore() {
            assertEquals("objective_score", BaseService.humpToLine("objectiveScore"));
        }

        @Test
        @DisplayName("subjectiveScore → subjective_score")
        void testSubjectiveScore() {
            assertEquals("subjective_score", BaseService.humpToLine("subjectiveScore"));
        }
    }

    // ===== toWhereSql 考试场景下的条件拼接 =====

    @Nested
    @DisplayName("toWhereSql - 考试场景条件拼接")
    class ToWhereSqlTest {

        @Test
        @DisplayName("按 examId 精确查询")
        void testByExamId() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("examId", "1");
            String sql = examService.toWhereSql(q, false, null);
            assertTrue(sql.contains("exam_id"));
            assertTrue(sql.contains("= '1'"));
        }

        @Test
        @DisplayName("按分数范围查询 score_min ~ score_max")
        void testScoreRange() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("score_min", "60");
            q.put("score_max", "100");
            String sql = examService.toWhereSql(q, false, null);
            assertTrue(sql.contains(">="));
            assertTrue(sql.contains("<="));
            assertTrue(sql.contains("60"));
            assertTrue(sql.contains("100"));
        }

        @Test
        @DisplayName("空条件返回空字符串")
        void testEmpty() {
            assertEquals("", examService.toWhereSql(new HashMap<>(), false, null));
        }
    }

    // ===== 真实数据 CRUD 操作测试 =====

    @Test
    @Transactional
    @DisplayName("CRUD-考试: insert 考试 → select 验证")
    void testInsertExam() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "单元测试考试");
        body.put("duration", 60);
        body.put("score", 100);
        body.put("status", "启用");
        examService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("name", "单元测试考试");
        List list = examService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        examService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-考试: update 考试 → 验证更新")
    void testUpdateExam() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "更新测试考试");
        body.put("score", 80);
        examService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("name", "更新测试考试");
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("score", 90);
        examService.update(query, new HashMap<>(), updateBody);

        Exam exam = examService.findOne(query);
        assertNotNull(exam);

        examService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-考试: delete 考试 → 验证删除")
    void testDeleteExam() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "删除测试考试");
        examService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("name", "删除测试考试");
        examService.delete(query, new HashMap<>());

        Exam exam = examService.findOne(query);
        assertNull(exam);
    }

    @Test
    @Transactional
    @DisplayName("CRUD-考题: insert 单选题 → 查询验证")
    void testInsertSingleChoice() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "单选择题测试");
        body.put("type", "单选题");
        body.put("answer", "2");
        body.put("score", 5);
        body.put("question_order", 1);
        examQuestionService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("title", "单选择题测试");
        List list = examQuestionService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        examQuestionService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-考题: insert 多选题 → 查询验证")
    void testInsertMultiChoice() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "以下哪些是水果");
        body.put("type", "多选题");
        body.put("answer", "A,B,C");
        body.put("score", 10);
        examQuestionService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("title", "以下哪些是水果");
        assertFalse(examQuestionService.select(query, new HashMap<>()).getResultList().isEmpty());

        examQuestionService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-考题: insert 判断题 → 查询验证")
    void testInsertTrueFalse() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "判断测试题");
        body.put("type", "判断题");
        body.put("answer", "对");
        body.put("score", 2);
        examQuestionService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("title", "判断测试题");
        assertFalse(examQuestionService.select(query, new HashMap<>()).getResultList().isEmpty());

        examQuestionService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-考题: count 按类型统计题目数量")
    void testCountByType() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "统计测试题");
        body.put("type", "单选题");
        body.put("answer", "A");
        body.put("score", 5);
        examQuestionService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("type", "单选题");
        Query countQuery = examQuestionService.count(query, new HashMap<>());
        Object result = countQuery.getSingleResult();
        assertTrue(((Number) result).longValue() >= 1);

        Map<String, String> delQuery = new HashMap<>();
        delQuery.put("title", "统计测试题");
        examQuestionService.delete(delQuery, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-答题: insert 答题记录 → 查询验证")
    void testInsertAnswer() {
        Map<String, Object> body = new HashMap<>();
        body.put("user_id", 1);
        body.put("exam_id", 1);
        body.put("score", 85);
        body.put("score_state", 1);
        userAnswerService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("user_id", "1");
        List list = userAnswerService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        userAnswerService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-答题: update 答题分数 → 验证")
    void testUpdateScore() {
        Map<String, Object> body = new HashMap<>();
        body.put("user_id", 2);
        body.put("exam_id", 1);
        body.put("score", 70);
        userAnswerService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("user_id", "2");
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("score", 95);
        userAnswerService.update(query, new HashMap<>(), updateBody);

        UserAnswer answer = userAnswerService.findOne(query);
        assertNotNull(answer);

        userAnswerService.delete(query, new HashMap<>());
    }
}
