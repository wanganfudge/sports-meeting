package com.sportsmeeting.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sportsmeeting.entity.Article;
import com.sportsmeeting.mapper.ArticleMapper;
import com.sportsmeeting.service.ArticleService;
import org.springframework.stereotype.Service;
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {}
