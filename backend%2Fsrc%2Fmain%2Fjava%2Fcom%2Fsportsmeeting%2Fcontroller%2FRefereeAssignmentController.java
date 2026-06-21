package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.RefereeAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refereeAssignment")
public class RefereeAssignmentController {
    @Autowired
    private RefereeAssignmentService refereeAssignmentService;
    @GetMapping
    public ApiResult<Page<RefereeAssignment>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(refereeAssignmentService.page(new Page<>(page, size)));
    }
    @GetMapping("/{id}")
    public ApiResult<RefereeAssignment> getById(@PathVariable Long id) { return ApiResult.ok(refereeAssignmentService.getById(id)); }
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody RefereeAssignment entity) { return ApiResult.ok(refereeAssignmentService.save(entity)); }
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody RefereeAssignment entity) { return ApiResult.ok(refereeAssignmentService.updateById(entity)); }
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) { return ApiResult.ok(refereeAssignmentService.removeById(id)); }
}
