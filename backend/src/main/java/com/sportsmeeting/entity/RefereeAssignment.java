package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("referee_assignment")
public class RefereeAssignment {
    @TableId(type = IdType.AUTO)
    private Long assignmentId;
    private Long refereeId;
    private Long sessionId;
    private String duty;
}