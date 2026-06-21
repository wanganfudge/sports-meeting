package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.MatchSession;
import com.sportsmeeting.mapper.MatchSessionMapper;
import com.sportsmeeting.service.MatchSessionService;
import org.springframework.stereotype.Service;
@Service
public class MatchSessionServiceImpl extends ServiceImpl<MatchSessionMapper, MatchSession> implements MatchSessionService {
    @Override
    public void autoRank(Long sessionId) {
        baseMapper.callAutoRank(sessionId);
    }
}