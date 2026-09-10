package com.hdfc.service;

import com.hdfc.dto.EnrollmentRequestDto;
import com.hdfc.dto.EnrollmentResponseDto;
import com.hdfc.entity.Course;
import com.hdfc.entity.Enrollment;
import com.hdfc.exception.CourseCapacityFullException;
import com.hdfc.exception.CourseNotFoundException;
import com.hdfc.exception.DuplicateEnrollmentException;
import com.hdfc.exception.EnrollmentNotFoundException;
import com.hdfc.mapper.EnrollmentMapper;
import com.hdfc.repository.CourseRepository;
import com.hdfc.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponseDto enrollEmployee(EnrollmentRequestDto dto) {
        // Rule 1: Course existence check
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course with ID " + dto.getCourseId() + " does not exist"));

        List<Enrollment> totalEnrollments = enrollmentRepository.findAll();

        // Rule 3: Check for duplicate active enrollments
        boolean exists = totalEnrollments.stream()
                .anyMatch(e -> e.getEmployeeId().equals(dto.getEmployeeId()) 
                        && e.getCourseId().equals(dto.getCourseId()) 
                        && !"CANCELLED".equals(e.getStatus()));
        if (exists) {
            throw new DuplicateEnrollmentException("Employee " + dto.getEmployeeId() + " is already enrolled in this course");
        }

        // Rule 2: Check course capacity rules
        long activeCount = totalEnrollments.stream()
                .filter(e -> e.getCourseId().equals(dto.getCourseId()) && !"CANCELLED".equals(e.getStatus()))
                .count();
        if (activeCount >= course.getMaxCapacity()) {
            throw new CourseCapacityFullException("Course capacity of " + course.getMaxCapacity() + " has been reached");
        }

        Enrollment enrollment = enrollmentMapper.toEntity(dto);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ENROLLED");

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponseDto(saved);
    }

    @Override
    public List<EnrollmentResponseDto> getAllEnrollments() {
        return enrollmentRepository.findAll().stream()
                .map(enrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDto getEnrollmentById(Integer id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment record with ID " + id + " not found"));
        return enrollmentMapper.toResponseDto(enrollment);
    }

    @Override
    public EnrollmentResponseDto cancelEnrollment(Integer id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment record with ID " + id + " not found"));
        enrollment.setStatus("CANCELLED");
        return enrollmentMapper.toResponseDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentResponseDto completeEnrollment(Integer id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment record with ID " + id + " not found"));
        enrollment.setStatus("COMPLETED");
        return enrollmentMapper.toResponseDto(enrollmentRepository.save(enrollment));
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByStatus(String status) {
        return enrollmentRepository.findAll().stream()
                .filter(e -> e.getStatus().equalsIgnoreCase(status))
                .map(enrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByEmployeeId(Integer employeeId) {
        return enrollmentRepository.findAll().stream()
                .filter(e -> e.getEmployeeId().equals(employeeId))
                .map(enrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getTotalEnrollmentCount() {
        return enrollmentRepository.findAll().stream().count();
    }
}
