package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.model.Lesson;
import com.example.backend_capstone_project.model.User;
import com.example.backend_capstone_project.repository.ILessonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LessonServiceTest {

    @Autowired
    private LessonService lessonService;

    @MockBean
    private ILessonRepository lessonRepository;

    private Lesson testLesson = Lesson.builder()
            .id(1)
            .status("Scheduled")
            .location("Court 1")
            .dateScheduled(new Date())
            .playerNeedsEquipment(true)
            .player(User.builder().id(2).firstName("John").lastName("Doe").build())
            .coach(User.builder().id(3).firstName("Jane").lastName("Doe").build())
            .build();

    private List<Lesson> testLessons = List.of(testLesson);

    @Test
    public void testAddLesson_Success() {
        when(lessonRepository.save(any(Lesson.class))).thenReturn(testLesson);

        Lesson savedLesson = lessonService.addLesson(testLesson);

        assertEquals(testLesson, savedLesson);
        verify(lessonRepository, times(1)).save(testLesson);
    }

    @Test
    public void testGetAllLessons_Success() {
        when(lessonRepository.findAll()).thenReturn(testLessons);

        List<Lesson> lessons = lessonService.getAllLessons();

        assertEquals(testLessons, lessons);
        verify(lessonRepository, times(1)).findAll();
    }

    @Test
    public void testGetLessonById_Success() {
        when(lessonRepository.findById(anyInt())).thenReturn(Optional.of(testLesson));

        Lesson lesson = lessonService.getLessonById(1);

        assertEquals(testLesson, lesson);
        verify(lessonRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateLesson_Success() {
        when(lessonRepository.existsById(anyInt())).thenReturn(true);
        when(lessonRepository.save(any(Lesson.class))).thenReturn(testLesson);

        Lesson updatedLesson = Lesson.builder()
                .status("Completed")
                .location("Court 2")
                .dateScheduled(new Date())
                .playerNeedsEquipment(false)
                .player(User.builder().id(2).firstName("John").lastName("Doe").build())
                .coach(User.builder().id(3).firstName("Jane").lastName("Doe").build())
                .build();

        Lesson savedLesson = lessonService.updateLesson(1, updatedLesson);

        assertEquals(testLesson, savedLesson);
        verify(lessonRepository, times(1)).existsById(1);
        verify(lessonRepository, times(1)).save(updatedLesson);
    }

    @Test
    public void testDeleteLesson_Success() {
        when(lessonRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(lessonRepository).deleteById(anyInt());

        lessonService.deleteLesson(1);

        verify(lessonRepository, times(1)).existsById(1);
        verify(lessonRepository, times(1)).deleteById(1);
    }
}

