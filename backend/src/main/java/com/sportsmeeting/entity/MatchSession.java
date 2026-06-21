package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("match_session")
public class MatchSession {
    @TableId(type = IdType.AUTO)
    private Long sessionId;
    private Long eventId;
    private String sessionName;
    private String matchDate;
    private String matchTime;
    private String venue;
    private String status;
    @TableField(exist = false)
    private String eventName;
}
