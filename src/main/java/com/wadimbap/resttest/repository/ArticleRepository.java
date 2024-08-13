package com.wadimbap.resttest.repository;

import com.wadimbap.resttest.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByPublishedDateBetween(LocalDate startDate, LocalDate endDate);
}
