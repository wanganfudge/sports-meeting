package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("staff")
public class Staff {
    @TableId(type = IdType.AUTO)
    private Long staffId;
    private String name;
    private String phone;
}