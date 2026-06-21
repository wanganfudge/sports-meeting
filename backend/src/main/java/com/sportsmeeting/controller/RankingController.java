package com.sportsmeeting.controller;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/athlete")
    public ApiResult<List<AthleteRanking>> athleteRanking() {
        List<AthleteRanking> list = jdbcTemplate.query("SELECT * FROM v_athlete_ranking", new BeanPropertyRowMapper<>(AthleteRanking.class));
        return ApiResult.ok(list);
    }

    @GetMapping("/dept")
    public ApiResult<List<DeptRanking>> deptRanking() {
        List<DeptRanking> list = jdbcTemplate.query("SELECT * FROM v_dept_ranking", new BeanPropertyRowMapper<>(DeptRanking.class));
        return ApiResult.ok(list);
    }
}
