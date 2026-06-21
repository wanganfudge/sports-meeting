package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.Participation;
import com.sportsmeeting.mapper.ParticipationMapper;
import com.sportsmeeting.service.ParticipationService;
import org.springframework.stereotype.Service;
@Service
public class ParticipationServiceImpl extends ServiceImpl<ParticipationMapper, Participation> implements ParticipationService {}
