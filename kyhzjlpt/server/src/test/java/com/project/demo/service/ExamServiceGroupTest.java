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
 * 考试模块 Service 测试
 * 覆盖: ExamService, ExamQuestionService, UserAnswerService
 */
@SpringBootTest
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
}
