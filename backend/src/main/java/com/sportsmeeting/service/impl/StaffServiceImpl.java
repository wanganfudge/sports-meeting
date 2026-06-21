package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.Staff;
import com.sportsmeeting.mapper.StaffMapper;
import com.sportsmeeting.service.StaffService;
import org.springframework.stereotype.Service;
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {}
