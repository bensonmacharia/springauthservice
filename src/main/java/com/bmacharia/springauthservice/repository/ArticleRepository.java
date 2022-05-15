package com.bmacharia.springauthservice.repository;

import com.bmacharia.springauthservice.model.Article;
import com.bmacharia.springauthservice.payload.ArticleQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByTitle(String title);

    Optional<Article> findById(Long id);

    Boolean existsByTitle(String title);

    @Query("SELECT new com.bmacharia.springauthservice.payload.ArticleQuery(a.id, a.body, a.title, u.username) FROM Article a INNER JOIN User u ON a.addedBy = u.id")
    List<ArticleQuery> getAllArticles();

    @Query("SELECT new com.bmacharia.springauthservice.payload.ArticleQuery(a.id, a.body, a.title, u.username) FROM Article a INNER JOIN User u ON a.addedBy = u.id AND a.id = ?1")
    ArticleQuery getArticleById(Long id);
}
