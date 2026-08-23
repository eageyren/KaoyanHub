package com.project.demo.service;

import com.project.demo.entity.Comment;
import com.project.demo.entity.Forum;
import com.project.demo.entity.ForumType;
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
 * 论坛模块 Service 测试
 * 覆盖: ForumService, ForumTypeService, CommentService, PraiseService, HitsService
 */
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisplayName("论坛模块 Service 测试")
public class ForumServiceGroupTest {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumTypeService forumTypeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private HitsService hitsService;

    // ===== Spring 注入验证 =====

    @Nested
    @DisplayName("Spring 容器注入验证")
    class InjectionTest {

        @Test
        @DisplayName("ForumService 注入成功")
        void forumServiceNotNull() {
            assertNotNull(forumService);
        }

        @Test
        @DisplayName("ForumTypeService 注入成功")
        void forumTypeServiceNotNull() {
            assertNotNull(forumTypeService);
        }

        @Test
        @DisplayName("CommentService 注入成功")
        void commentServiceNotNull() {
            assertNotNull(commentService);
        }

        @Test
        @DisplayName("PraiseService 注入成功")
        void praiseServiceNotNull() {
            assertNotNull(praiseService);
        }

        @Test
        @DisplayName("HitsService 注入成功")
        void hitsServiceNotNull() {
            assertNotNull(hitsService);
        }
    }

    // ===== humpToLine 在论坛场景的验证 =====

    @Nested
    @DisplayName("humpToLine - 论坛字段名转换")
    class HumpToLineTest {

        @Test
        @DisplayName("forumId → forum_id")
        void testForumId() {
            assertEquals("forum_id", BaseService.humpToLine("forumId"));
        }

        @Test
        @DisplayName("forumType → forum_type")
        void testForumType() {
            assertEquals("forum_type", BaseService.humpToLine("forumType"));
        }

        @Test
        @DisplayName("replyToId → reply_to_id")
        void testReplyToId() {
            assertEquals("reply_to_id", BaseService.humpToLine("replyToId"));
        }

        @Test
        @DisplayName("createTime → create_time")
        void testCreateTime() {
            assertEquals("create_time", BaseService.humpToLine("createTime"));
        }

        @Test
        @DisplayName("sourceTable → source_table")
        void testSourceTable() {
            assertEquals("source_table", BaseService.humpToLine("sourceTable"));
        }
    }

    // ===== toWhereSql 论坛场景条件拼接 =====

    @Nested
    @DisplayName("toWhereSql - 论坛场景条件拼接")
    class ToWhereSqlTest {

        @Test
        @DisplayName("按 forumId 精确查询")
        void testByForumId() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("forumId", "5");
            String sql = forumService.toWhereSql(q, false, null);
            assertTrue(sql.contains("forum_id"));
            assertTrue(sql.contains("= '5'"));
        }

        @Test
        @DisplayName("按标题模糊搜索（like=true）")
        void testTitleLike() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("title", "考研");
            String sql = forumService.toWhereSql(q, true, null);
            assertTrue(sql.contains("LIKE '%考研%'"));
        }

        @Test
        @DisplayName("按来源表过滤（sourceTable）")
        void testSourceTable() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("sourceTable", "forum");
            String sql = praiseService.toWhereSql(q, false, null);
            assertTrue(sql.contains("source_table"));
        }

        @Test
        @DisplayName("按 userId 查询评论")
        void testCommentByUserId() {
            Map<String, String> q = new LinkedHashMap<>();
            q.put("userId", "1");
            String sql = commentService.toWhereSql(q, false, null);
            assertTrue(sql.contains("user_id"));
            assertTrue(sql.contains("= '1'"));
        }

        @Test
        @DisplayName("空条件返回空字符串")
        void testEmpty() {
            assertEquals("", forumService.toWhereSql(new HashMap<>(), false, null));
        }
    }

    // ===== encryption 在论坛模块的验证 =====

    @Nested
    @DisplayName("encryption - 加密方法验证")
    class EncryptionTest {

        @Test
        @DisplayName("forumService.encryption 返回 32 位 MD5")
        void testFormat() {
            String result = forumService.encryption("forumTest");
            assertNotNull(result);
            assertEquals(32, result.length());
        }

        @Test
        @DisplayName("各 Service encryption 结果一致")
        void testConsistency() {
            String input = "consistency_test";
            assertEquals(forumService.encryption(input), commentService.encryption(input));
            assertEquals(praiseService.encryption(input), hitsService.encryption(input));
        }
    }

    // ===== 真实数据 CRUD 操作测试 =====

    @Test
    @Transactional
    @DisplayName("CRUD-评论: insert → 查询验证")
    void testInsertComment() {
        Map<String, Object> body = new HashMap<>();
        body.put("user_id", 1);
        body.put("content", "这是一条测试评论");
        body.put("source_table", "forum");
        body.put("source_field", "forum_id");
        body.put("source_id", 1);
        commentService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("content", "这是一条测试评论");
        List list = commentService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        commentService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-分类: insert → 查询验证")
    void testInsertType() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "测试分类");
        body.put("description", "测试用分类");
        forumTypeService.insert(body);

        Map<String, String> query = new HashMap<>();
        query.put("name", "测试分类");
        List list = forumTypeService.select(query, new HashMap<>()).getResultList();
        assertFalse(list.isEmpty());

        forumTypeService.delete(query, new HashMap<>());
    }

    @Test
    @Transactional
    @DisplayName("CRUD-分类: count 分类数量")
    void testCountTypes() {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "计数测试分类");
        forumTypeService.insert(body);

        Query countQuery = forumTypeService.count(new HashMap<>(), new HashMap<>());
        Object result = countQuery.getSingleResult();
        assertTrue(((Number) result).longValue() >= 1);

        Map<String, String> delQuery = new HashMap<>();
        delQuery.put("name", "计数测试分类");
        forumTypeService.delete(delQuery, new HashMap<>());
    }
}
