package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.Athlete;
import com.sportsmeeting.mapper.AthleteMapper;
import com.sportsmeeting.service.AthleteService;
import org.springframework.stereotype.Service;
@Service
public class AthleteServiceImpl extends ServiceImpl<AthleteMapper, Athlete> implements AthleteService {}
