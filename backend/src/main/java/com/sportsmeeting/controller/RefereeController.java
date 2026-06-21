package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.RefereeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/referee")
public class RefereeController {
    @Autowired
    private RefereeService refereeService;

    @GetMapping
    public ApiResult<Page<Referee>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(refereeService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResult<Referee> getById(@PathVariable Long id) {
        return ApiResult.ok(refereeService.getById(id));
    }

    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Referee entity) {
        return ApiResult.ok(refereeService.save(entity));
    }

    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Referee entity) {
        return ApiResult.ok(refereeService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(refereeService.removeById(id));
    }
}
