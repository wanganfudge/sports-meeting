package com.sportsmeeting.entity;
import lombok.Data;
@Data
public class DeptRanking {
    private Long deptId;
    private String deptName;
    private Integer totalScore;
    private Integer athleteCount;
    private Integer participationCount;
}