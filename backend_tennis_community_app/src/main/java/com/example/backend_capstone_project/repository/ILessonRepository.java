package com.example.backend_capstone_project.repository;

import com.example.backend_capstone_project.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonRepository extends JpaRepository<Lesson, Integer> {
}
