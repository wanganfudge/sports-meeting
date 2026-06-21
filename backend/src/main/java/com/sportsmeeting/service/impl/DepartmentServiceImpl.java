package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.Department;
import com.sportsmeeting.mapper.DepartmentMapper;
import com.sportsmeeting.service.DepartmentService;
import org.springframework.stereotype.Service;
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {}
