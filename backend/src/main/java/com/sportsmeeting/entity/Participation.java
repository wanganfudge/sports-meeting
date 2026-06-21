package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("participation")
public class Participation {
    @TableId(type = IdType.AUTO)
    private Long participationId;
    private Long sessionId;
    private Long athleteId;
    private Integer trackNo;
    private Double result;
    private Integer rank;
    private Integer score;
    @TableField(exist = false)
    private String athleteName;
    @TableField(exist = false)
    private String athleteGroup;
}
