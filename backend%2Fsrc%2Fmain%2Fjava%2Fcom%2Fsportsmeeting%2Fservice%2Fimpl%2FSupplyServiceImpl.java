package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.Supply;
import com.sportsmeeting.mapper.SupplyMapper;
import com.sportsmeeting.service.SupplyService;
import org.springframework.stereotype.Service;
@Service
public class SupplyServiceImpl extends ServiceImpl<SupplyMapper, Supply> implements SupplyService {}
