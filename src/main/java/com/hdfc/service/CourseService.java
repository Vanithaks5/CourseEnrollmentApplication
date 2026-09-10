package com.hdfc.service;

import com.hdfc.dto.CourseRequestDto;
import com.hdfc.dto.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto dto);
    CourseResponseDto getCourseById(Integer id);
    List<CourseResponseDto> getAllCourses();
    CourseResponseDto updateCourse(Integer id, CourseRequestDto dto);
    void deleteCourse(Integer id);
    List<CourseResponseDto> getCoursesByTrainer(String trainerName);
    List<CourseResponseDto> getCoursesWithFeesLessThan(Double amount);
    long getTotalCourseCount();
    CourseResponseDto getMostPopularCourse();
}
