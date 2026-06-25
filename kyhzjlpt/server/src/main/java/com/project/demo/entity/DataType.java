package com.project.demo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import com.project.demo.entity.base.BaseEntity;
import java.io.Serializable;
import lombok.*;
import javax.persistence.*;


/**
 * 资料类型：(DataType)表实体类
 *
 */
@Setter
@Getter
@Entity(name = "DataType")
public class DataType implements Serializable {

    // DataType编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_type_id")
    private Integer data_type_id;

    // 资料类型
    @Basic
    private String data_type;













    // 更新时间
    @Basic
    private Timestamp update_time;

    // 创建时间
    @Basic
    private Timestamp create_time;

}
