package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("athlete")
public class Athlete {
    @TableId(type = IdType.AUTO)
    private Long athleteId;
    private String name;
    private String gender;
    private Long classId;
    private String groupName;
    private String phone;
    @TableField(exist = false)
    private String className;
    @TableField(exist = false)
    private String deptName;
}
