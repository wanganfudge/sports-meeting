package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("event")
public class Event {
    @TableId(type = IdType.AUTO)
    private Long eventId;
    private String eventName;
    private String eventType;
    private String genderGroup;
    private String unit;
    private Double record;
}