package com.hdfc.controller;

import com.hdfc.dto.CourseResponseDto;
import com.hdfc.service.CourseService;
import com.hdfc.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping("/course-count")
    public ResponseEntity<Long> getCourseCount() {
        return ResponseEntity.ok(courseService.getTotalCourseCount());
    }

    @GetMapping("/enrollment-count")
    public ResponseEntity<Long> getEnrollmentCount() {
        return ResponseEntity.ok(enrollmentService.getTotalEnrollmentCount());
    }

    @GetMapping("/most-popular-course")
    public ResponseEntity<CourseResponseDto> getMostPopularCourse() {
        return ResponseEntity.ok(courseService.getMostPopularCourse());
    }
}

