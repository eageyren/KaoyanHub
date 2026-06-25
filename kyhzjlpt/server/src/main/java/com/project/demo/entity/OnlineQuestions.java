package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 * 在线提问：(OnlineQuestions)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "OnlineQuestions")
public class OnlineQuestions implements Serializable {

    // OnlineQuestions编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "online_questions_id")
    private Integer online_questions_id;

    // 问题编号
    @Basic
    private String question_no;
    // 提问用户
    @Basic
    private Integer ask_the_user;
    // 用户姓名
    @Basic
    private String user_name;
    // 问题描述
    @Basic
    private String problem_description;
    // 问题附件
    @Basic
    private String problem_attachment;



    // 审核状态
    @Basic
    private String examine_state;







    // 审核回复
    @Basic
    private String examine_reply;



    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
