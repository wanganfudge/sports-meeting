package com.sportsmeeting.entity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
@Data
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Long articleId;
    private String title;
    private String content;
    private String authorName;
    private Long deptId;
    private String publishTime;
    @TableField(exist = false)
    private String deptName;
}