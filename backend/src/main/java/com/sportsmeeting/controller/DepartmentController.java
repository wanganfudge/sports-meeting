package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ApiResult<Page<Department>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(departmentService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResult<Department> getById(@PathVariable Long id) {
        return ApiResult.ok(departmentService.getById(id));
    }

    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Department entity) {
        return ApiResult.ok(departmentService.save(entity));
    }

    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Department entity) {
        return ApiResult.ok(departmentService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(departmentService.removeById(id));
    }
}
