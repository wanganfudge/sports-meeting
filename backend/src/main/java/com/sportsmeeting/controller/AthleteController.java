package com.sportsmeeting.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sportsmeeting.entity.*;
import com.sportsmeeting.service.AthleteService;
import com.sportsmeeting.service.SchoolClassService;
import com.sportsmeeting.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/athlete")
public class AthleteController {
    @Autowired
    private AthleteService athleteService;
    @Autowired
    private SchoolClassService classService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ApiResult<Page<Athlete>> list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "20") int size) {
        Page<Athlete> result = athleteService.page(new Page<>(page, size));
        List<Athlete> enriched = result.getRecords().stream().map(a -> {
            SchoolClass sc = classService.getById(a.getClassId());
            if (sc != null) {
                a.setClassName(sc.getClassName());
                Department d = departmentService.getById(sc.getDeptId());
                if (d != null) a.setDeptName(d.getDeptName());
            }
            return a;
        }).collect(Collectors.toList());
        result.setRecords(enriched);
        return ApiResult.ok(result);
    }

    @GetMapping("/{id}")
    public ApiResult<Athlete> getById(@PathVariable Long id) { return ApiResult.ok(athleteService.getById(id)); }
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody Athlete entity) { return ApiResult.ok(athleteService.save(entity)); }
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody Athlete entity) { return ApiResult.ok(athleteService.updateById(entity)); }
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) { return ApiResult.ok(athleteService.removeById(id)); }
}
