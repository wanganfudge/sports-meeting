package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.DutyScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dutySchedule")
public class DutyScheduleController {
    @Autowired
    private DutyScheduleService dutyScheduleService;
    @GetMapping
    public ApiResult<Page<DutySchedule>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(dutyScheduleService.page(new Page<>(page, size)));
    }
    @GetMapping("/{id}")
    public ApiResult<DutySchedule> getById(@PathVariable Long id) { return ApiResult.ok(dutyScheduleService.getById(id)); }
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody DutySchedule entity) { return ApiResult.ok(dutyScheduleService.save(entity)); }
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody DutySchedule entity) { return ApiResult.ok(dutyScheduleService.updateById(entity)); }
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) { return ApiResult.ok(dutyScheduleService.removeById(id)); }
}
