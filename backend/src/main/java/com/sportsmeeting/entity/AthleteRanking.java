package com.sportsmeeting.entity;
import lombok.Data;
@Data
public class AthleteRanking {
    private Long athleteId;
    private String athleteName;
    private String gender;
    private String groupName;
    private String className;
    private String deptName;
    private Integer totalScore;
    private Integer eventCount;
}