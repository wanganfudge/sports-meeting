package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("supply")
public class Supply {
    @TableId(type = IdType.AUTO)
    private Long supplyId;
    private String name;
    private Integer quantity;
    private String unit;
}