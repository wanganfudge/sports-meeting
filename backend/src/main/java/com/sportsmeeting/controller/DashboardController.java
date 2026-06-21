package com.sportsmeeting.controller;
import com.sportsmeeting.entity.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public ApiResult<Map<String, Object>> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("athleteCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM athlete", Long.class));
        data.put("eventCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM event", Long.class));
        data.put("sessionCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM match_session", Long.class));
        data.put("finishedCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM match_session WHERE status='已结束'", Long.class));
        data.put("articleCount", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM article", Long.class));
        return ApiResult.ok(data);
    }
}
