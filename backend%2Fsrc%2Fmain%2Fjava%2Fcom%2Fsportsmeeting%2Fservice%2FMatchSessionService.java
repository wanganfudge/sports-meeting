package com.sportsmeeting.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sportsmeeting.entity.MatchSession;
public interface MatchSessionService extends IService<MatchSession> {
    void autoRank(Long sessionId);
}