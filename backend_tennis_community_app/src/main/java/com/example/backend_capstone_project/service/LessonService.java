package com.example.backend_capstone_project.service;

import com.example.backend_capstone_project.model.Lesson;
import com.example.backend_capstone_project.repository.ILessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LessonService {

    @Autowired
    ILessonRepository lessonRepository;

    // ======= ADD =======
    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    // ======= GET ALL =======
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    // ======= GET BY ID =======
    public Lesson getLessonById(Integer id) {
        return lessonRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    // ======= UPDATE =======
    public Lesson updateLesson(Integer id, Lesson updatedLesson) {
        if (!lessonRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        updatedLesson.setId(id); // Ensure the ID of the updated lesson matches the ID in the path
        return lessonRepository.save(updatedLesson);
    }

    // ======= DELETE =======
    public void deleteLesson(Integer id) {
        if (!lessonRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        lessonRepository.deleteById(id);
    }
}
