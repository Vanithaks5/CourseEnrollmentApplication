package com.hdfc.repository;

import com.hdfc.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CourseRepository {
    private final Map<Integer, Course> courses = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public Course save(Course course) {
        if (course.getCourseId() == null) {
            course.setCourseId(idGenerator.incrementAndGet());
        }
        courses.put(course.getCourseId(), course);
        return course;
    }

    public Optional<Course> findById(Integer id) {
        return Optional.ofNullable(courses.get(id));
    }

    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }

    public boolean deleteById(Integer id) {
        return courses.remove(id) != null;
    }
}

