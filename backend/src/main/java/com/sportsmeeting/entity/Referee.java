package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("referee")
public class Referee {
    @TableId(type = IdType.AUTO)
    private Long refereeId;
    private String name;
    private String role;
}