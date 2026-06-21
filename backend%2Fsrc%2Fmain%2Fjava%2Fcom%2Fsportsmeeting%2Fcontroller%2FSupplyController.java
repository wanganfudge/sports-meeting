package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/supply")
public class SupplyController {
    @Autowired
    private SupplyService supplyService;

    @GetMapping
    public ApiResult<Page<Supply>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(supplyService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResult<Supply> getById(@PathVariable Long id) {
        return ApiResult.ok(supplyService.getById(id));
    }

    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Supply entity) {
        return ApiResult.ok(supplyService.save(entity));
    }

    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Supply entity) {
        return ApiResult.ok(supplyService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(supplyService.removeById(id));
    }
}
