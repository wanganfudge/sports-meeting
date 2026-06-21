package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping
    public ApiResult<Page<Staff>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(staffService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResult<Staff> getById(@PathVariable Long id) {
        return ApiResult.ok(staffService.getById(id));
    }

    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Staff entity) {
        return ApiResult.ok(staffService.save(entity));
    }

    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Staff entity) {
        return ApiResult.ok(staffService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(staffService.removeById(id));
    }
}
