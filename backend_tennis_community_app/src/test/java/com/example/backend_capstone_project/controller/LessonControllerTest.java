package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.model.Lesson;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.service.LessonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LessonController.class)
@AutoConfigureMockMvc
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonService lessonService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testPlayer = User.builder()
            .id(1)
            .firstName("PlayerFirstName")
            .lastName("PlayerLastName")
            .email("player@example.com")
            .password("password123")
            .isPlayer(true)
            .isCoach(false)
            .build();

    private User testCoach = User.builder()
            .id(2)
            .firstName("CoachFirstName")
            .lastName("CoachLastName")
            .email("coach@example.com")
            .password("password123")
            .isPlayer(false)
            .isCoach(true)
            .build();

    private Lesson testLesson = Lesson.builder()
            .id(1)
            .status("Scheduled")
            .location("Court 1")
            .dateScheduled(new Date())
            .playerNeedsEquipment(true)
            .player(testPlayer)
            .coach(testCoach)
            .build();

    private List<Lesson> testLessons = List.of(testLesson);

    @Test
    public void testAddLesson_Success() throws Exception {
        when(lessonService.addLesson(any(Lesson.class))).thenReturn(testLesson);

        mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testLesson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testLesson.getId()))
                .andExpect(jsonPath("$.status").value(testLesson.getStatus()))
                .andExpect(jsonPath("$.location").value(testLesson.getLocation()))
                .andExpect(jsonPath("$.player.id").value(testPlayer.getId()))
                .andExpect(jsonPath("$.coach.id").value(testCoach.getId()));
    }

    @Test
    public void testGetAllLessons_Success() throws Exception {
        when(lessonService.getAllLessons()).thenReturn(testLessons);

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testLesson.getId()))
                .andExpect(jsonPath("$[0].status").value(testLesson.getStatus()))
                .andExpect(jsonPath("$[0].location").value(testLesson.getLocation()))
                .andExpect(jsonPath("$[0].player.id").value(testPlayer.getId()))
                .andExpect(jsonPath("$[0].coach.id").value(testCoach.getId()));
    }

    @Test
    public void testGetLessonById_Success() throws Exception {
        when(lessonService.getLessonById(anyInt())).thenReturn(testLesson);

        mockMvc.perform(get("/lessons/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testLesson.getId()))
                .andExpect(jsonPath("$.status").value(testLesson.getStatus()))
                .andExpect(jsonPath("$.location").value(testLesson.getLocation()))
                .andExpect(jsonPath("$.player.id").value(testPlayer.getId()))
                .andExpect(jsonPath("$.coach.id").value(testCoach.getId()));
    }

    @Test
    public void testUpdateLesson_Success() throws Exception {
        when(lessonService.updateLesson(anyInt(), any(Lesson.class))).thenReturn(testLesson);

        mockMvc.perform(put("/lessons/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testLesson)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testLesson.getId()))
                .andExpect(jsonPath("$.status").value(testLesson.getStatus()))
                .andExpect(jsonPath("$.location").value(testLesson.getLocation()))
                .andExpect(jsonPath("$.player.id").value(testPlayer.getId()))
                .andExpect(jsonPath("$.coach.id").value(testCoach.getId()));
    }

    @Test
    public void testDeleteLesson_Success() throws Exception {
        doNothing().when(lessonService).deleteLesson(anyInt());

        mockMvc.perform(delete("/lessons/{id}", 1))
                .andExpect(status().isNoContent());

        verify(lessonService, times(1)).deleteLesson(1);
    }
}

