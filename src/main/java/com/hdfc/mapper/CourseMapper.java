package com.hdfc.mapper;

import com.hdfc.dto.CourseRequestDto;
import com.hdfc.dto.CourseResponseDto;
import com.hdfc.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequestDto dto) {
        Course course = new Course();
        course.setCourseName(dto.getCourseName());
        course.setTrainerName(dto.getTrainerName());
        course.setDurationInDays(dto.getDurationInDays());
        course.setMaxCapacity(dto.getMaxCapacity());
        course.setFees(dto.getFees());
        return course;
    }

    public CourseResponseDto toResponseDto(Course course) {
        CourseResponseDto dto = new CourseResponseDto();
        dto.setCourseId(course.getCourseId());
        dto.setCourseName(course.getCourseName());
        dto.setTrainerName(course.getTrainerName());
        dto.setDurationInDays(course.getDurationInDays());
        dto.setMaxCapacity(course.getMaxCapacity());
        dto.setFees(course.getFees());
        return dto;
    }
}

