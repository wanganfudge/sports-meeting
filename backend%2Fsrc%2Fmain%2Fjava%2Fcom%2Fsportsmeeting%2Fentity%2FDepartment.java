package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("department")
public class Department {
    @TableId(type = IdType.AUTO)
    private Long deptId;
    private String deptName;
}