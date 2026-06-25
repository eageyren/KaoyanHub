package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 * 资料分享：(DataSharing)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "DataSharing")
public class DataSharing implements Serializable {

    // DataSharing编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_sharing_id")
    private Integer data_sharing_id;

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
    // 分享对象
    @Basic
    private Integer shared_objects;













    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
