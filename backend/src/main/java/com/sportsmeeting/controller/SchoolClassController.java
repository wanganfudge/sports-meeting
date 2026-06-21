package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/class")
public class SchoolClassController {
    @Autowired
    private SchoolClassService classService;

    @GetMapping
    public ApiResult<Page<SchoolClass>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(classService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResult<SchoolClass> getById(@PathVariable Long id) {
        return ApiResult.ok(classService.getById(id));
    }

    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SchoolClass entity) {
        return ApiResult.ok(classService.save(entity));
    }

    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SchoolClass entity) {
        return ApiResult.ok(classService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(classService.removeById(id));
    }
}
