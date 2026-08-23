package com.project.demo.controller.mockito;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.Exam;
import com.project.demo.entity.ExamQuestion;
import com.project.demo.entity.UserAnswer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 考试系统白盒测试 —— 实体类字段与业务逻辑验证
 */
@DisplayName("考试系统实体 白盒测试")
public class ExamControllerTest {

    // ==================== Exam 实体测试 ====================

    @Nested
    @DisplayName("Exam 实体")
    class ExamEntityTest {

        @Test
        @DisplayName("TC-EX01: Exam 完整属性设置与获取")
        void testExamGetSet() {
            Exam exam = new Exam();
            exam.setExam_id(1);
            exam.setName("2025年考研英语模拟卷");
            exam.setDuration(120);
            exam.setScore(150.0);
            exam.setStatus("启用");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            exam.setCreateTime(now);
            exam.setUpdateTime(now);

            assertEquals(1, exam.getExam_id());
            assertEquals("2025年考研英语模拟卷", exam.getName());
            assertEquals(120, exam.getDuration());
            assertEquals(150.0, exam.getScore());
            assertEquals("启用", exam.getStatus());
            assertEquals(now, exam.getCreateTime());
            assertEquals(now, exam.getUpdateTime());
        }

        @Test
        @DisplayName("TC-EX02: Exam 默认值（未设置字段为 null）")
        void testExamDefaults() {
            Exam exam = new Exam();
            assertNull(exam.getExam_id());
            assertNull(exam.getName());
            assertNull(exam.getDuration());
            assertNull(exam.getScore());
            assertNull(exam.getStatus());
        }

        @Test
        @DisplayName("TC-EX03: Exam 状态值 - 启用")
        void testExamStatusEnabled() {
            Exam exam = new Exam();
            exam.setStatus("启用");
            assertEquals("启用", exam.getStatus());
        }

        @Test
        @DisplayName("TC-EX04: Exam 状态值 - 禁用")
        void testExamStatusDisabled() {
            Exam exam = new Exam();
            exam.setStatus("禁用");
            assertEquals("禁用", exam.getStatus());
        }

        @Test
        @DisplayName("TC-EX05: Exam 总分为 0")
        void testExamZeroScore() {
            Exam exam = new Exam();
            exam.setScore(0.0);
            assertEquals(0.0, exam.getScore());
        }

        @Test
        @DisplayName("TC-EX06: Exam 答题时长为负值（异常数据边界）")
        void testExamNegativeDuration() {
            Exam exam = new Exam();
            exam.setDuration(-10);
            assertEquals(-10, exam.getDuration());
            // 说明系统未对 duration 做非负校验
        }
    }

    // ==================== ExamQuestion 实体测试 ====================

    @Nested
    @DisplayName("ExamQuestion 试题实体")
    class ExamQuestionEntityTest {

        @Test
        @DisplayName("TC-EQ01: 单选题完整属性")
        void testSingleChoiceQuestion() {
            ExamQuestion q = new ExamQuestion();
            q.setExam_question_id(1);
            q.setType("单选题");
            q.setTitle("下列哪个不是Java关键字？");
            q.setQuestion_item("A.class|B.goto|C.sizeof|D.extends");
            q.setAnswer("C");
            q.setScore(5.0);
            q.setQuestion_order(1);
            q.setExam_id(100);

            assertEquals(1, q.getExam_question_id());
            assertEquals("单选题", q.getType());
            assertEquals("下列哪个不是Java关键字？", q.getTitle());
            assertEquals("A.class|B.goto|C.sizeof|D.extends", q.getQuestion_item());
            assertEquals("C", q.getAnswer());
            assertEquals(5.0, q.getScore());
            assertEquals(1, q.getQuestion_order());
            assertEquals(100, q.getExam_id());
        }

        @Test
        @DisplayName("TC-EQ02: 多选题属性")
        void testMultiChoiceQuestion() {
            ExamQuestion q = new ExamQuestion();
            q.setType("多选题");
            q.setAnswer("A,B,D");
            assertEquals("多选题", q.getType());
            assertEquals("A,B,D", q.getAnswer());
        }

        @Test
        @DisplayName("TC-EQ03: 判断题属性")
        void testTrueFalseQuestion() {
            ExamQuestion q = new ExamQuestion();
            q.setType("判断题");
            q.setAnswer("正确");
            assertEquals("判断题", q.getType());
        }

        @Test
        @DisplayName("TC-EQ04: 填空题属性")
        void testFillBlankQuestion() {
            ExamQuestion q = new ExamQuestion();
            q.setType("填空题");
            q.setAnswer("Spring Boot");
            q.setQuestion_item(null);
            assertEquals("填空题", q.getType());
            assertNull(q.getQuestion_item());
        }

        @Test
        @DisplayName("TC-EQ05: 主观题属性")
        void testSubjectiveQuestion() {
            ExamQuestion q = new ExamQuestion();
            q.setType("主观题");
            q.setTitle("请论述面向对象三大特性");
            q.setAnswer("封装、继承、多态...");
            q.setScore(20.0);
            assertEquals("主观题", q.getType());
            assertEquals(20.0, q.getScore());
        }

        @Test
        @DisplayName("TC-EQ06: 分值为 0 的题目（边界）")
        void testZeroScoreQuestion() {
            ExamQuestion q = new ExamQuestion();
            q.setScore(0.0);
            assertEquals(0.0, q.getScore());
        }
    }

    // ==================== UserAnswer 用户答题实体测试 ====================

    @Nested
    @DisplayName("UserAnswer 用户答题实体")
    class UserAnswerEntityTest {

        @Test
        @DisplayName("TC-UA01: 完整答题记录")
        void testFullAnswer() {
            UserAnswer ua = new UserAnswer();
            ua.setUser_answer_id(1);
            ua.setUser_id(10);
            ua.setExam_id(100);
            ua.setScore(85.5);
            ua.setScore_state(1);
            ua.setObjective_score(65.0);
            ua.setSubjective_score(20.5);
            ua.setNickname("考研战士");
            ua.setAnswers("{\"1\":\"A\",\"2\":\"B,C\"}");
            ua.setScore_detail("{\"1\":5,\"2\":10}");

            assertEquals(1, ua.getUser_answer_id());
            assertEquals(10, ua.getUser_id());
            assertEquals(100, ua.getExam_id());
            assertEquals(85.5, ua.getScore());
            assertEquals(1, ua.getScore_state());
            assertEquals(65.0, ua.getObjective_score());
            assertEquals(20.5, ua.getSubjective_score());
            assertEquals("考研战士", ua.getNickname());
        }

        @Test
        @DisplayName("TC-UA02: 未批改状态 (score_state=0)")
        void testUngraded() {
            UserAnswer ua = new UserAnswer();
            ua.setScore_state(0);
            ua.setObjective_score(60.0);
            ua.setSubjective_score(null);

            assertEquals(0, ua.getScore_state());
            assertNull(ua.getSubjective_score());
        }

        @Test
        @DisplayName("TC-UA03: 总分计算验证 (客观+主观)")
        void testScoreCalculation() {
            UserAnswer ua = new UserAnswer();
            ua.setObjective_score(70.0);
            ua.setSubjective_score(25.0);
            double total = ua.getObjective_score() + ua.getSubjective_score();
            ua.setScore(total);
            assertEquals(95.0, ua.getScore());
        }

        @Test
        @DisplayName("TC-UA04: 0 分答题记录")
        void testZeroScore() {
            UserAnswer ua = new UserAnswer();
            ua.setScore(0.0);
            ua.setObjective_score(0.0);
            ua.setSubjective_score(0.0);
            assertEquals(0.0, ua.getScore());
        }

        @Test
        @DisplayName("TC-UA05: answers 为 null（未提交答案边界）")
        void testNullAnswers() {
            UserAnswer ua = new UserAnswer();
            assertNull(ua.getAnswers());
            assertNull(ua.getScore_detail());
        }
    }

    // ==================== BaseController success/error 响应测试 ====================

    @Nested
    @DisplayName("BaseController 响应方法")
    class BaseControllerResponseTest {

        // 创建一个可实例化的 BaseController 用于测试 success/error 方法
        private final TestableBaseController controller = new TestableBaseController();

        @Test
        @DisplayName("TC-BC01: success(null) → result=null")
        void testSuccessNull() {
            java.util.Map<String, Object> result = controller.success(null);
            assertNull(result.get("result"));
        }

        @Test
        @DisplayName("TC-BC02: success(Integer) → result=Integer")
        void testSuccessInteger() {
            java.util.Map<String, Object> result = controller.success(1);
            assertEquals(1, result.get("result"));
        }

        @Test
        @DisplayName("TC-BC03: success(String) → result=String")
        void testSuccessString() {
            java.util.Map<String, Object> result = controller.success("退出登录成功！");
            assertEquals("退出登录成功！", result.get("result"));
        }

        @Test
        @DisplayName("TC-BC04: error 返回结构正确")
        void testError() {
            java.util.Map<String, Object> result = controller.error(30000, "用户已存在");
            assertTrue(result.containsKey("error"));
            @SuppressWarnings("unchecked")
            java.util.Map<String, Object> error = (java.util.Map<String, Object>) result.get("error");
            assertEquals(30000, error.get("code"));
            assertEquals("用户已存在", error.get("message"));
        }

        @Test
        @DisplayName("TC-BC05: success(List单元素) → 解包")
        void testSuccessSingleList() {
            java.util.List<Integer> list = java.util.Arrays.asList(42);
            java.util.Map<String, Object> result = controller.success(list);
            assertEquals(42, result.get("result"));
        }
    }

    // 可实例化的 BaseController 子类
    static class TestableBaseController extends BaseController<Exam, com.project.demo.service.ExamService> {
    }
}

