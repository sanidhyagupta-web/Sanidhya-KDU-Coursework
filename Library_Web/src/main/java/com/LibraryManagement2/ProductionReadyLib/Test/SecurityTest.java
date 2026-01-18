package com.LibraryManagement2.ProductionReadyLib.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VALID_BOOK_JSON = """
        {
          "title": "Spring Boot in Action"
        }
        """;

    @Test
    @WithMockUser(username = "member1", roles = "MEMBER")
    void memberCannotPostBooks() throws Exception {
        mockMvc.perform(post("/books/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BOOK_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Librarian",roles = "LIBRARIAN")
    void librarianCanPostBooks() throws Exception{
        mockMvc.perform(post("/books/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_BOOK_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void unauthenticatedUserGets401() throws Exception{
        mockMvc.perform(post("/books/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_BOOK_JSON))
                .andExpect(status().isUnauthorized());
    }
}
