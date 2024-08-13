package com.wadimbap.resttest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wadimbap.resttest.dto.ArticleCreateDTO;
import com.wadimbap.resttest.dto.ArticleResponseDTO;
import com.wadimbap.resttest.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private ArticleCreateDTO createArticleDTO(String title, String author, LocalDate publishedDate) {
        ArticleCreateDTO dto = new ArticleCreateDTO();
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setContent("Sample Content");
        dto.setPublishedDate(publishedDate);
        return dto;
    }

    private ArticleResponseDTO createArticleResponseDTO(Long id, String title, String author, LocalDate publishedDate) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setAuthor(author);
        dto.setContent("Sample Content");
        dto.setPublishedDate(publishedDate);
        return dto;
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testCreateArticle() throws Exception {
        ArticleCreateDTO createDTO = createArticleDTO("Test Title", "Author", LocalDate.now());
        ArticleResponseDTO responseDTO = createArticleResponseDTO(1L, "Test Title", "Author", LocalDate.now());

        when(articleService.createArticle(any(ArticleCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/articles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishedDate").value(LocalDate.now().toString()));
    }



    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testListArticles() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        ArticleResponseDTO responseDTO = createArticleResponseDTO(1L, "Test Title", "Author", LocalDate.now());
        Page<ArticleResponseDTO> page = new PageImpl<>(List.of(responseDTO), pageable, 1);

        when(articleService.getAllArticles(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value("Test Title"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetArticleStats() throws Exception {
        LocalDate today = LocalDate.now();
        Map<LocalDate, Long> stats = IntStream.range(0, 7)
                .mapToObj(today::minusDays)
                .collect(Collectors.toMap(date -> date, date -> 1L));

        when(articleService.countArticlesByDate(any(LocalDate.class))).thenAnswer(invocation -> {
            LocalDate date = invocation.getArgument(0);
            return stats.get(date);
        });

        mockMvc.perform(MockMvcRequestBuilders.get("/api/articles/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(7));
    }
}
