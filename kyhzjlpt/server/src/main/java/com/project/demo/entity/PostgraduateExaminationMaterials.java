package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 * 考研资料：(PostgraduateExaminationMaterials)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "PostgraduateExaminationMaterials")
public class PostgraduateExaminationMaterials implements Serializable {

    // PostgraduateExaminationMaterials编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postgraduate_examination_materials_id")
    private Integer postgraduate_examination_materials_id;

    // 资料名称
    @Basic
    private String data_name;
    // 封面
    @Basic
    private String cover;
    // 资料类型
    @Basic
    private String data_type;
    // 知识点
    @Basic
    private String knowledge_points;
    // 资料文件
    @Basic
    private String information_documents;

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
