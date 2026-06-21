package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public ApiResult<Page<Event>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResult.ok(eventService.page(new Page<>(page, size)));
    }

    @GetMapping("/{id}")
    public ApiResult<Event> getById(@PathVariable Long id) {
        return ApiResult.ok(eventService.getById(id));
    }

    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Event entity) {
        return ApiResult.ok(eventService.save(entity));
    }

    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Event entity) {
        return ApiResult.ok(eventService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(eventService.removeById(id));
    }
}
