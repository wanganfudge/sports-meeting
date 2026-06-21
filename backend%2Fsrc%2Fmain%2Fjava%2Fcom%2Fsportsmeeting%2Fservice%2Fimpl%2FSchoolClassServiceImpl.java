package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.SchoolClass;
import com.sportsmeeting.mapper.SchoolClassMapper;
import com.sportsmeeting.service.SchoolClassService;
import org.springframework.stereotype.Service;
@Service
public class SchoolClassServiceImpl extends ServiceImpl<SchoolClassMapper, SchoolClass> implements SchoolClassService {}
