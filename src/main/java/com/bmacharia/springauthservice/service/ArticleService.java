package com.bmacharia.springauthservice.service;

import com.bmacharia.springauthservice.model.Article;
import com.bmacharia.springauthservice.payload.ArticleQuery;

import java.util.List;

public interface ArticleService {
    List<ArticleQuery> listAllArticles();
    ArticleQuery getArticleById(Long id);

    Article getArticle(String title);

    Article addArticle(Article article);
}
