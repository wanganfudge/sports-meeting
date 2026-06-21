package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.ArticleService;
import com.sportsmeeting.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ApiResult<Page<Article>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Article> result = articleService.page(new Page<>(page, size));
        for (Article a : result.getRecords()) {
            Department d = departmentService.getById(a.getDeptId());
            if (d != null) a.setDeptName(d.getDeptName());
        }
        return ApiResult.ok(result);
    }

    @GetMapping("/{id}")
    public ApiResult<Article> getById(@PathVariable Long id) {
        Article a = articleService.getById(id);
        if (a != null) {
            Department d = departmentService.getById(a.getDeptId());
            if (d != null) a.setDeptName(d.getDeptName());
        }
        return ApiResult.ok(a);
    }

    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Article entity) {
        return ApiResult.ok(articleService.save(entity));
    }

    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Article entity) {
        return ApiResult.ok(articleService.updateById(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(articleService.removeById(id));
    }
}