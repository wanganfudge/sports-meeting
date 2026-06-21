package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.RefereeAssignment;
import com.sportsmeeting.mapper.RefereeAssignmentMapper;
import com.sportsmeeting.service.RefereeAssignmentService;
import org.springframework.stereotype.Service;
@Service
public class RefereeAssignmentServiceImpl extends ServiceImpl<RefereeAssignmentMapper, RefereeAssignment> implements RefereeAssignmentService {}
