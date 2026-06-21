package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.DutySchedule;
import com.sportsmeeting.mapper.DutyScheduleMapper;
import com.sportsmeeting.service.DutyScheduleService;
import org.springframework.stereotype.Service;
@Service
public class DutyScheduleServiceImpl extends ServiceImpl<DutyScheduleMapper, DutySchedule> implements DutyScheduleService {}
