package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("duty_schedule")
public class DutySchedule {
    @TableId(type = IdType.AUTO)
    private Long dutyId;
    private Long staffId;
    private String dutyDate;
    private String startTime;
    private String endTime;
    private String location;
}