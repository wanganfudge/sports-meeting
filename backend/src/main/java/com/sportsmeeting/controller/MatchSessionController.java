package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.MatchSessionService;
import com.sportsmeeting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matchSession")
public class MatchSessionController {
    @Autowired
    private MatchSessionService matchSessionService;
    @Autowired
    private EventService eventService;

    @GetMapping
    public ApiResult<Page<MatchSession>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        Page<MatchSession> result = matchSessionService.page(new Page<>(page, size));
        result.getRecords().forEach(ms -> {
            Event e = eventService.getById(ms.getEventId());
            if (e != null) ms.setEventName(e.getEventName());
        });
        return ApiResult.ok(result);
    }
    @GetMapping("/{id}")
    public ApiResult<MatchSession> getById(@PathVariable Long id) { return ApiResult.ok(matchSessionService.getById(id)); }
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody MatchSession entity) { return ApiResult.ok(matchSessionService.save(entity)); }
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody MatchSession entity) { return ApiResult.ok(matchSessionService.updateById(entity)); }
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) { return ApiResult.ok(matchSessionService.removeById(id)); }
    @PostMapping("/autoRank/{sessionId}")
    public ApiResult<String> autoRank(@PathVariable Long sessionId) {
        matchSessionService.autoRank(sessionId);
        return ApiResult.ok("排名完成");
    }
}