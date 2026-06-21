package com.sportsmeeting.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sportsmeeting.entity.MatchSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchSessionMapper extends BaseMapper<MatchSession> {
    @Select("CALL sp_auto_rank(#{sessionId})")
    void callAutoRank(Long sessionId);
}