package com.hdfc.service;

import com.hdfc.dto.CourseRequestDto;
import com.hdfc.dto.CourseResponseDto;
import com.hdfc.entity.Course;
import com.hdfc.entity.Enrollment;
import com.hdfc.exception.CourseNotFoundException;
import com.hdfc.mapper.CourseMapper;
import com.hdfc.repository.CourseRepository;
import com.hdfc.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto dto) {
        Course course = courseMapper.toEntity(dto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponseDto(savedCourse);
    }

    @Override
    public CourseResponseDto getCourseById(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + id + " not found"));
        return courseMapper.toResponseDto(course);
    }

    @Override
    public List<CourseResponseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDto updateCourse(Integer id, CourseRequestDto dto) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + id + " not found"));
        
        existingCourse.setCourseName(dto.getCourseName());
        existingCourse.setTrainerName(dto.getTrainerName());
        existingCourse.setDurationInDays(dto.getDurationInDays());
        existingCourse.setMaxCapacity(dto.getMaxCapacity());
        existingCourse.setFees(dto.getFees());

        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toResponseDto(updatedCourse);
    }

    @Override
    public void deleteCourse(Integer id) {
        if (!courseRepository.deleteById(id)) {
            throw new CourseNotFoundException("Course with ID " + id + " not found");
        }
    }

    @Override
    public List<CourseResponseDto> getCoursesByTrainer(String trainerName) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getTrainerName().equalsIgnoreCase(trainerName))
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponseDto> getCoursesWithFeesLessThan(Double amount) {
        return courseRepository.findAll().stream()
                .filter(c -> c.getFees() < amount)
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalCourseCount() {
        return courseRepository.findAll().stream().count();
    }

    @Override
    public CourseResponseDto getMostPopularCourse() {
        List<Enrollment> activeEnrollments = enrollmentRepository.findAll().stream()
                .filter(e -> "ENROLLED".equals(e.getStatus()) || "COMPLETED".equals(e.getStatus()))
                .collect(Collectors.toList());

        if (activeEnrollments.isEmpty()) {
            throw new CourseNotFoundException("No enrollments found to determine popularity");
        }

        Map<Integer, Long> courseEnrollmentCounts = activeEnrollments.stream()
                .collect(Collectors.groupingBy(Enrollment::getCourseId, Collectors.counting()));

        Integer popularCourseId = courseEnrollmentCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new CourseNotFoundException("No popular course found"));

        return getCourseById(popularCourseId);
    }
}

