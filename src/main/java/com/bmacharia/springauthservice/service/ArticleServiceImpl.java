package com.bmacharia.springauthservice.service;

import com.bmacharia.springauthservice.model.Article;
import com.bmacharia.springauthservice.payload.ArticleQuery;
import com.bmacharia.springauthservice.repository.ArticleRepository;
import com.bmacharia.springauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public List<ArticleQuery> listAllArticles() {
        log.info("Fetching all articles");
        return articleRepository.getAllArticles();
    }


    @Override
    public Article getArticle(String title) {
        log.info("Fetching article {} details", title);
        return articleRepository.findByTitle(title);
    }

    @Override
    public ArticleQuery getArticleById(Long id) {
        log.info("Fetching article {}", id);
        return articleRepository.getArticleById(id);
    }

    @Override
    public Article addArticle(Article article) {
        log.info("Saving new article {} to the database", article.getTitle());
        return articleRepository.save(article);
    }
}
