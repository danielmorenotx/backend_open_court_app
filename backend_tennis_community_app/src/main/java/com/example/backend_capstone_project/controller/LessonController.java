package com.example.backend_capstone_project.controller;

import com.example.backend_capstone_project.model.Lesson;
import com.example.backend_capstone_project.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    LessonService lessonService;

    // ======= ADD =======
    @PostMapping
    public Lesson addLesson(@RequestBody Lesson lesson) {
        return lessonService.addLesson(lesson);
    }

    // ======= GET ALL =======
    @GetMapping
    public List<Lesson> getAllLessons() {
        return lessonService.getAllLessons();
    }

    // ======= GET BY ID =======
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Integer id) {
        try {
            Lesson lesson = lessonService.getLessonById(id);
            return ResponseEntity.ok(lesson);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ======= UPDATE =======
    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Integer id, @RequestBody Lesson updatedLesson) {
        try {
            Lesson lesson = lessonService.updateLesson(id, updatedLesson);
            return ResponseEntity.ok(lesson);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ======= DELETE =======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Integer id) {
        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

