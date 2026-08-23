package com.project.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 空壳控制器参数化测试 —— 验证注解配置正确性
 * <p>
 * 这些控制器无自定义逻辑，纯继承 BaseController。测试验证它们:
 * 1. @RestController 注解存在
 * 2. @RequestMapping 路径正确
 * 3. 继承自 BaseController
 * </p>
 */
@DisplayName("空壳控制器参数化验证")
public class GenericControllerTest {

    static Stream<Arguments> controllerProvider() {
        return Stream.of(
                Arguments.of(AccessTokenController.class, "access_token"),
                Arguments.of(AuthController.class, "auth"),
                Arguments.of(CollectController.class, "collect"),
                Arguments.of(CommentController.class, "comment"),
                Arguments.of(ExamController.class, "exam"),
                Arguments.of(ExamQuestionController.class, "exam_question"),
                Arguments.of(ForumController.class, "forum"),
                Arguments.of(ForumTypeController.class, "forum_type"),
                Arguments.of(HitsController.class, "hits"),
                Arguments.of(NavAdminController.class, "nav_admin"),
                Arguments.of(NoticeController.class, "notice"),
                Arguments.of(PraiseController.class, "praise"),
                Arguments.of(SlidesController.class, "slides"),
                Arguments.of(UploadController.class, "upload"),
                Arguments.of(UserAnswerController.class, "user_answer"),
                Arguments.of(UserGroupController.class, "user_group")
        );
    }

    @ParameterizedTest(name = "{1}: @RestController + @RequestMapping 验证")
    @MethodSource("controllerProvider")
    @DisplayName("控制器注解和路径验证")
    void testControllerAnnotations(Class<?> controllerClass, String expectedPath) {
        RestController restAnnotation = controllerClass.getAnnotation(RestController.class);
        assertNotNull(restAnnotation, controllerClass.getSimpleName() + " 应有 @RestController");

        RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
        assertNotNull(requestMapping, controllerClass.getSimpleName() + " 应有 @RequestMapping");
        assertArrayEquals(new String[]{expectedPath}, requestMapping.value(),
                controllerClass.getSimpleName() + " 路径应为 " + expectedPath);
    }

    @ParameterizedTest(name = "{1}: 继承 BaseController")
    @MethodSource("controllerProvider")
    @DisplayName("控制器继承关系验证")
    void testExtendsBaseController(Class<?> controllerClass, String expectedPath) {
        Class<?> superclass = controllerClass.getSuperclass();
        assertEquals("com.project.demo.controller.base.BaseController", superclass.getName(),
                controllerClass.getSimpleName() + " 应继承 BaseController");
    }
}
