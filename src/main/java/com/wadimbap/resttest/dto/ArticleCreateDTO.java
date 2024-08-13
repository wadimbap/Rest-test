package com.wadimbap.resttest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ArticleCreateDTO {
    private String title;
    private String author;
    private String content;
    private LocalDate publishedDate;
}
