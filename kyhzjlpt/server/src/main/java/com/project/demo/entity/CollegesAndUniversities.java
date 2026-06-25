package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 * 报考院校：(CollegesAndUniversities)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "CollegesAndUniversities")
public class CollegesAndUniversities implements Serializable {

    // CollegesAndUniversities编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "colleges_and_universities_id")
    private Integer colleges_and_universities_id;

    // 院校名称
    @Basic
    private String name_of_institution;
    // 封面
    @Basic
    private String cover;
    // 院校专业
    @Basic
    private String college_major;
    // 历年分数线
    @Basic
    private String score_over_the_years;
    // 院校详情
    @Basic
    private String details_of_institutions;

    // 点击数
    @Basic
    private Integer hits;

    // 点赞数
    @Basic
    private Integer praise_len;











    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
