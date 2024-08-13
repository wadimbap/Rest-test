package com.wadimbap.resttest.service;

import com.wadimbap.resttest.dto.ArticleCreateDTO;
import com.wadimbap.resttest.dto.ArticleResponseDTO;
import com.wadimbap.resttest.model.Article;
import com.wadimbap.resttest.repository.ArticleRepository;
import com.wadimbap.resttest.dto.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper mapper;

    public ArticleResponseDTO createArticle(ArticleCreateDTO articleCreateDTO) {
        Article article = mapper.toEntity(articleCreateDTO);
        article = articleRepository.save(article);
        return mapper.toDto(article);
    }

    public Page<ArticleResponseDTO> getAllArticles(Pageable pageable) {
        return articleRepository
                .findAll(pageable)
                .map(mapper::toDto);
    }

    public long countArticlesByDate(LocalDate date) {
        return articleRepository
                .findAllByPublishedDateBetween(date, date.plusDays(1))
                .size();
    }
}
