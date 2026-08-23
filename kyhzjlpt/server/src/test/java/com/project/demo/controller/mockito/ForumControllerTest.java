package com.project.demo.controller.mockito;

import com.project.demo.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 论坛模块白盒测试 —— 实体字段验证 + 业务逻辑覆盖
 */
@DisplayName("论坛 & 互动模块 白盒测试")
public class ForumControllerTest {

    // ==================== Forum 帖子实体测试 ====================

    @Nested
    @DisplayName("Forum 帖子实体")
    class ForumEntityTest {

        @Test
        @DisplayName("TC-FM01: 帖子完整属性设置与获取")
        void testForumFullProperties() {
            Forum forum = new Forum();
            forum.setForumId(1);
            forum.setDisplay(1);
            forum.setUserId(100);
            forum.setNickname("考研加油");
            forum.setPraise_len(0);
            forum.setHits(0);
            forum.setTitle("考研经验分享");
            forum.setKeywords("考研,经验");
            forum.setDescription("分享备考过程");
            forum.setUrl(null);
            forum.setTag("经验贴");
            forum.setImg("/api/upload/cover.jpg");
            forum.setContent("<p>详细考研经验分享</p>");
            forum.setType("经验分享");
            forum.setAvatar("/api/upload/avatar.jpg");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            forum.setCreateTime(now);
            forum.setUpdateTime(now);

            assertEquals(1, forum.getForumId());
            assertEquals("考研经验分享", forum.getTitle());
            assertEquals("考研加油", forum.getNickname());
            assertEquals(0, forum.getPraise_len());
            assertEquals(0, forum.getHits());
            assertEquals("经验分享", forum.getType());
            assertEquals("<p>详细考研经验分享</p>", forum.getContent());
        }

        @Test
        @DisplayName("TC-FM02: 帖子点赞数递增模拟")
        void testPraiseIncrement() {
            Forum forum = new Forum();
            forum.setPraise_len(0);
            // 模拟点赞递增
            forum.setPraise_len(forum.getPraise_len() + 1);
            assertEquals(1, forum.getPraise_len());
            forum.setPraise_len(forum.getPraise_len() + 1);
            assertEquals(2, forum.getPraise_len());
        }

        @Test
        @DisplayName("TC-FM03: 帖子浏览量递增模拟")
        void testHitsIncrement() {
            Forum forum = new Forum();
            forum.setHits(0);
            forum.setHits(forum.getHits() + 1);
            assertEquals(1, forum.getHits());
        }

        @Test
        @DisplayName("TC-FM04: 帖子标题为空（边界测试）")
        void testEmptyTitle() {
            Forum forum = new Forum();
            forum.setTitle("");
            assertEquals("", forum.getTitle());
        }

        @Test
        @DisplayName("TC-FM05: 帖子默认值均为 null")
        void testForumDefaults() {
            Forum forum = new Forum();
            assertNull(forum.getForumId());
            assertNull(forum.getTitle());
            assertNull(forum.getContent());
            assertNull(forum.getNickname());
            assertNull(forum.getType());
        }
    }

    // ==================== Comment 评论实体测试 ====================

    @Nested
    @DisplayName("Comment 评论实体")
    class CommentEntityTest {

        @Test
        @DisplayName("TC-CM01: 评论完整属性")
        void testCommentFullProperties() {
            Comment comment = new Comment();
            comment.setCommentId(1);
            comment.setUserId(10);
            comment.setReplyToId(0);
            comment.setContent("写得非常好！");
            comment.setNickname("学长");
            comment.setAvatar("/api/upload/avatar.jpg");
            comment.setSourceTable("forum");
            comment.setSourceField("forum_id");
            comment.setSourceId(100);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            comment.setCreateTime(now);

            assertEquals(1, comment.getCommentId());
            assertEquals(10, comment.getUserId());
            assertEquals(0, comment.getReplyToId());
            assertEquals("写得非常好！", comment.getContent());
            assertEquals("forum", comment.getSourceTable());
            assertEquals(100, comment.getSourceId());
        }

        @Test
        @DisplayName("TC-CM02: 评论回复（replyToId 非零）")
        void testReplyComment() {
            Comment comment = new Comment();
            comment.setReplyToId(5);
            assertEquals(5, comment.getReplyToId());
        }

        @Test
        @DisplayName("TC-CM03: 评论内容为空字符串")
        void testEmptyContent() {
            Comment comment = new Comment();
            comment.setContent("");
            assertEquals("", comment.getContent());
        }

        @Test
        @DisplayName("TC-CM04: 评论关联资料表（sourceTable=postgraduate_examination_materials）")
        void testCommentOnMaterial() {
            Comment comment = new Comment();
            comment.setSourceTable("postgraduate_examination_materials");
            comment.setSourceField("postgraduate_examination_materials_id");
            comment.setSourceId(50);
            assertEquals("postgraduate_examination_materials", comment.getSourceTable());
        }
    }

    // ==================== Collect 收藏实体测试 ====================

    @Nested
    @DisplayName("Collect 收藏实体")
    class CollectEntityTest {

        @Test
        @DisplayName("TC-CO01: 收藏属性设置")
        void testCollectProperties() {
            Collect collect = new Collect();
            collect.setCollectId(1);
            collect.setUserId(10);
            collect.setSourceTable("forum");
            collect.setSourceField("forum_id");
            collect.setSourceId(100);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            collect.setCreateTime(now);

            assertEquals(1, collect.getCollectId());
            assertEquals(10, collect.getUserId());
            assertEquals(100, collect.getSourceId());
        }
    }

    // ==================== CollegesAndUniversities 院校实体测试 ====================

    @Nested
    @DisplayName("CollegesAndUniversities 报考院校实体")
    class CollegesEntityTest {

        @Test
        @DisplayName("TC-CU01: 院校完整属性")
        void testCollegesFullProperties() {
            CollegesAndUniversities college = new CollegesAndUniversities();
            college.setColleges_and_universities_id(1);
            college.setName_of_institution("清华大学");
            college.setCover("/api/upload/tsinghua.jpg");
            college.setCollege_major("计算机科学与技术");
            college.setScore_over_the_years("2023:380,2024:385");
            college.setDetails_of_institutions("<p>清华大学详细介绍</p>");
            college.setHits(1000);
            college.setPraise_len(500);

            assertEquals(1, college.getColleges_and_universities_id());
            assertEquals("清华大学", college.getName_of_institution());
            assertEquals("计算机科学与技术", college.getCollege_major());
            assertEquals(1000, college.getHits());
            assertEquals(500, college.getPraise_len());
        }

        @Test
        @DisplayName("TC-CU02: 院校点击数初始化")
        void testInitialHits() {
            CollegesAndUniversities college = new CollegesAndUniversities();
            college.setHits(0);
            college.setPraise_len(0);
            assertEquals(0, college.getHits());
            assertEquals(0, college.getPraise_len());
        }
    }

    // ==================== PostgraduateExaminationMaterials 考研资料实体测试 ====================

    @Nested
    @DisplayName("PostgraduateExaminationMaterials 考研资料实体")
    class MaterialsEntityTest {

        @Test
        @DisplayName("TC-PM01: 资料完整属性")
        void testMaterialsFullProperties() {
            PostgraduateExaminationMaterials m = new PostgraduateExaminationMaterials();
            m.setPostgraduate_examination_materials_id(1);
            m.setData_name("2025年考研政治真题解析");
            m.setCover("/api/upload/cover.jpg");
            m.setData_type("真题");
            m.setKnowledge_points("马原,毛中特");
            m.setInformation_documents("/api/upload/doc.pdf");
            m.setHits(200);
            m.setPraise_len(50);

            assertEquals(1, m.getPostgraduate_examination_materials_id());
            assertEquals("2025年考研政治真题解析", m.getData_name());
            assertEquals("真题", m.getData_type());
            assertEquals(200, m.getHits());
            assertEquals(50, m.getPraise_len());
        }

        @Test
        @DisplayName("TC-PM02: 资料名称为空")
        void testEmptyName() {
            PostgraduateExaminationMaterials m = new PostgraduateExaminationMaterials();
            m.setData_name("");
            assertEquals("", m.getData_name());
        }

        @Test
        @DisplayName("TC-PM03: 资料文件为 null")
        void testNullFile() {
            PostgraduateExaminationMaterials m = new PostgraduateExaminationMaterials();
            assertNull(m.getInformation_documents());
        }
    }

    // ==================== OnlineQuestions 在线提问实体测试 ====================

    @Nested
    @DisplayName("OnlineQuestions 在线提问实体")
    class OnlineQuestionsEntityTest {

        @Test
        @DisplayName("TC-OQ01: 提问完整属性")
        void testOnlineQuestionsProperties() {
            OnlineQuestions q = new OnlineQuestions();
            q.setOnline_questions_id(1);
            q.setQuestion_no("Q20250101");
            q.setAsk_the_user(10);
            q.setUser_name("考研同学");
            q.setProblem_description("考研数学线性代数如何复习？");
            q.setProblem_attachment("/api/upload/attachment.pdf");
            q.setExamine_state("待审核");
            q.setExamine_reply(null);

            assertEquals("Q20250101", q.getQuestion_no());
            assertEquals("待审核", q.getExamine_state());
            assertNull(q.getExamine_reply());
        }

        @Test
        @DisplayName("TC-OQ02: 审核通过状态")
        void testExamineStatePassed() {
            OnlineQuestions q = new OnlineQuestions();
            q.setExamine_state("已通过");
            q.setExamine_reply("已通过审核");
            assertEquals("已通过", q.getExamine_state());
            assertNotNull(q.getExamine_reply());
        }

        @Test
        @DisplayName("TC-OQ03: 审核拒绝状态")
        void testExamineStateRejected() {
            OnlineQuestions q = new OnlineQuestions();
            q.setExamine_state("已拒绝");
            q.setExamine_reply("问题描述不够详细");
            assertEquals("已拒绝", q.getExamine_state());
        }
    }
}

