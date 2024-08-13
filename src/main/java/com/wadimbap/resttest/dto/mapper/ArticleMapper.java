package com.wadimbap.resttest.dto.mapper;

import com.wadimbap.resttest.dto.ArticleCreateDTO;
import com.wadimbap.resttest.dto.ArticleResponseDTO;
import com.wadimbap.resttest.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public Article toEntity(ArticleCreateDTO articleCreateDTO) {
        Article article = new Article();
        article.setTitle(articleCreateDTO.getTitle());
        article.setAuthor(articleCreateDTO.getAuthor());
        article.setContent(articleCreateDTO.getContent());
        article.setPublishedDate(articleCreateDTO.getPublishedDate());
        return article;
    }

    public ArticleResponseDTO toDto(Article article) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setAuthor(article.getAuthor());
        dto.setContent(article.getContent());
        dto.setPublishedDate(article.getPublishedDate());
        return dto;
    }
}