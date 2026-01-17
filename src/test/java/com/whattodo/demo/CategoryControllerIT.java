package com.whattodo.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerIT {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    // ---------- Use Case: GET /categories ----------

    @Test
    void getCategories_returns200_andJsonArray() throws Exception {
        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createCategory_thenGetCategories_containsIt() throws Exception {
        var body = om.createObjectNode();
        body.put("name", "Uni");

        mvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk());

        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItem("Uni")));
    }

    // ---------- Use Case: POST /categories ----------

    @Test
    void  createCategory_emptyName_returns4xx() throws Exception {
        var body = om.createObjectNode();
        body.put("name", "");

        mvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().is4xxClientError());
    }

    // ---------- Use Case: DELETE /categories/{name} ----------

    @Test
    void deleteCategory_removesItFromList() throws Exception {
        var body = om.createObjectNode();
        body.put("name", "DeleteCat");

        mvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk());

        mvc.perform(delete("/categories/DeleteCat"))
                .andExpect(status().isOk());

        mvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(hasItem("DeleteCat"))));
    }
}
