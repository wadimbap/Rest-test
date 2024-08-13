package com.wadimbap.resttest.controller;

import com.wadimbap.resttest.dto.ArticleCreateDTO;
import com.wadimbap.resttest.dto.ArticleResponseDTO;
import com.wadimbap.resttest.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @Secured("ROLE_USER")
    @PostMapping("/create")
    public ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody ArticleCreateDTO articleCreateDTO) {
        ArticleResponseDTO response = articleService.createArticle(articleCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ArticleResponseDTO>> listArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleResponseDTO> allArticles = articleService.getAllArticles(pageable);

        return ResponseEntity.ok(allArticles);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/stats")
    public ResponseEntity<Map<LocalDate, Long>> getArticleStats() {

        LocalDate today = LocalDate.now();
        Map<LocalDate, Long> stats = IntStream.range(0, 7)
                .mapToObj(today::minusDays)
                .collect(Collectors.toMap(date -> date, articleService::countArticlesByDate));

        return ResponseEntity.ok(stats);
    }
}