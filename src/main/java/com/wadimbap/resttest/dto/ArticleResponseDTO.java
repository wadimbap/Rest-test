package com.wadimbap.resttest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDate publishedDate;
}
