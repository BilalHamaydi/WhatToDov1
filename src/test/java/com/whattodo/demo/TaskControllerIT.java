package com.whattodo.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

/**
 * Integration Tests (MockMvc) gegen H2 (Profile "test")
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerIT {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @BeforeEach
    void setup() {
        // Falls du in deinem Projekt Repos hast: hier könnte man DB leeren.
        // Wenn du create-drop nutzt, reicht meist Test-Isolation bereits.
    }

    // ---------- Use Case: GET /tasks ----------

    @Test
    void getTasks_returns200_andJsonArray() throws Exception {
        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createTask_thenGetTasks_containsCreatedTaskName() throws Exception {
        var body = om.createObjectNode();
        body.put("taskName", "JUnit Task");
        body.put("important", false);
        body.put("done", false);
        body.put("category", "");
        body.put("color", "#0d6efd");
        body.put("date", "2026-01-20");

        mvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.taskName").value("JUnit Task"));

        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].taskName", hasItem("JUnit Task")));
    }

    // ---------- Use Case: POST /tasks ----------

    @Test
    void createTask_missingName_returns4xx() throws Exception {
        var body = om.createObjectNode();
        body.put("taskName", ""); // invalid
        body.put("important", false);
        body.put("done", false);

        mvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createTask_withDate_isPersistedInResponse() throws Exception {
        var body = om.createObjectNode();
        body.put("taskName", "Mit Datum");
        body.put("important", false);
        body.put("done", false);
        body.put("date", "2026-01-01");
        body.put("color", "#198754");

        mvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("2026-01-01"))
                .andExpect(jsonPath("$.color").value("#198754"));
    }

    // ---------- Use Case: PATCH /tasks/{id}/done ----------

    @Test
    void toggleDone_setsDoneTrue() throws Exception {
        // create first
        var body = om.createObjectNode();
        body.put("taskName", "Toggle");
        body.put("important", false);
        body.put("done", false);

        String createdJson = mvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        long id = om.readTree(createdJson).get("id").asLong();

        mvc.perform(patch("/tasks/" + id + "/done?done=true"))
                .andExpect(status().isOk());
    }

    @Test
    void toggleDone_unknownId_returns4xxOr404() throws Exception {
        mvc.perform(patch("/tasks/999999/done?done=true"))
                .andExpect(status().is4xxClientError());
    }

    // ---------- Use Case: DELETE /tasks/{id} ----------

    @Test
    void deleteTask_removesItFromGetTasks() throws Exception {
        var body = om.createObjectNode();
        body.put("taskName", "DeleteMe");
        body.put("important", false);
        body.put("done", false);

        String createdJson = mvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        long id = om.readTree(createdJson).get("id").asLong();

        mvc.perform(delete("/tasks/" + id))
                .andExpect(status().isOk());

        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].taskName", not(hasItem("DeleteMe"))));
    }

    @Test
    void deleteTask_unknownId_returns4xxOr404() throws Exception {
        mvc.perform(delete("/tasks/999999"))
                .andExpect(status().is4xxClientError());
    }
}
