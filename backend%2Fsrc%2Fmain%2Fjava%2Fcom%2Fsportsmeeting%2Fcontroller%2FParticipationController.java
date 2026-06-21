package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.ParticipationService;
import com.sportsmeeting.service.AthleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/participation")
public class ParticipationController {
    @Autowired
    private ParticipationService participationService;
    @Autowired
    private AthleteService athleteService;

    @GetMapping
    public ApiResult<Page<Participation>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(required = false) Long sessionId) {
        QueryWrapper<Participation> qw = new QueryWrapper<>();
        if (sessionId != null) qw.eq("session_id", sessionId);
        Page<Participation> result = participationService.page(new Page<>(page, size), qw);
        result.getRecords().forEach(p -> {
            Athlete a = athleteService.getById(p.getAthleteId());
            if (a != null) { p.setAthleteName(a.getName()); p.setAthleteGroup(a.getGroupName()); }
        });
        return ApiResult.ok(result);
    }

    @GetMapping("/session/{sessionId}")
    public ApiResult<List<Participation>> listBySession(@PathVariable Long sessionId) {
        QueryWrapper<Participation> qw = new QueryWrapper<>();
        qw.eq("session_id", sessionId).orderByAsc("track_no");
        List<Participation> list = participationService.list(qw);
        list.forEach(p -> {
            Athlete a = athleteService.getById(p.getAthleteId());
            if (a != null) { p.setAthleteName(a.getName()); p.setAthleteGroup(a.getGroupName()); }
        });
        return ApiResult.ok(list);
    }

    @GetMapping("/{id}")
    public ApiResult<Participation> getById(@PathVariable Long id) { return ApiResult.ok(participationService.getById(id)); }
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Participation entity) { return ApiResult.ok(participationService.save(entity)); }
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Participation entity) { return ApiResult.ok(participationService.updateById(entity)); }
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) { return ApiResult.ok(participationService.removeById(id)); }
}
