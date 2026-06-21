package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.Referee;
import com.sportsmeeting.mapper.RefereeMapper;
import com.sportsmeeting.service.RefereeService;
import org.springframework.stereotype.Service;
@Service
public class RefereeServiceImpl extends ServiceImpl<RefereeMapper, Referee> implements RefereeService {}
