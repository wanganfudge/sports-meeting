package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("class")
public class SchoolClass {
    @TableId(type = IdType.AUTO)
    private Long classId;
    private String className;
    private Long deptId;
    private String grade;
}