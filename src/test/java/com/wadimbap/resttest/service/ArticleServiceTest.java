package com.wadimbap.resttest.service;

import com.wadimbap.resttest.dto.ArticleCreateDTO;
import com.wadimbap.resttest.dto.ArticleResponseDTO;
import com.wadimbap.resttest.model.Article;
import com.wadimbap.resttest.repository.ArticleRepository;
import com.wadimbap.resttest.dto.mapper.ArticleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateArticle() {
        ArticleCreateDTO createDTO = new ArticleCreateDTO();
        createDTO.setTitle("Test Title");
        createDTO.setAuthor("Author");
        createDTO.setContent("Content");
        createDTO.setPublishedDate(LocalDate.now());

        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Title");
        article.setAuthor("Author");
        article.setContent("Content");
        article.setPublishedDate(LocalDate.now());

        ArticleResponseDTO responseDTO = new ArticleResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("Test Title");
        responseDTO.setAuthor("Author");
        responseDTO.setContent("Content");
        responseDTO.setPublishedDate(LocalDate.now());

        when(articleMapper.toEntity(createDTO)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        when(articleMapper.toDto(article)).thenReturn(responseDTO);

        ArticleResponseDTO result = articleService.createArticle(createDTO);
        assertEquals(responseDTO, result);
    }

    @Test
    public void testGetAllArticles() {
        Pageable pageable = PageRequest.of(0, 10);
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Test Title");
        article.setAuthor("Author");
        article.setContent("Content");
        article.setPublishedDate(LocalDate.now());

        ArticleResponseDTO responseDTO = new ArticleResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("Test Title");
        responseDTO.setAuthor("Author");
        responseDTO.setContent("Content");
        responseDTO.setPublishedDate(LocalDate.now());

        List<Article> articles = List.of(article);
        Page<Article> page = new PageImpl<>(articles, pageable, articles.size());
        when(articleRepository.findAll(pageable)).thenReturn(page);
        when(articleMapper.toDto(article)).thenReturn(responseDTO);

        Page<ArticleResponseDTO> result = articleService.getAllArticles(pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(responseDTO, result.getContent().get(0));
    }

    @Test
    public void testCountArticlesByDate() {
        LocalDate date = LocalDate.now();
        when(articleRepository.findAllByPublishedDateBetween(date, date.plusDays(1))).thenReturn(List.of(new Article()));

        long count = articleService.countArticlesByDate(date);
        assertEquals(1, count);
    }
}
