package com.hdfc.controller;

import com.hdfc.dto.EnrollmentRequestDto;
import com.hdfc.dto.EnrollmentResponseDto;
import com.hdfc.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
 @RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> enrollEmployee(@Valid @RequestBody EnrollmentRequestDto dto) {
        return new ResponseEntity<>(enrollmentService.enrollEmployee(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto> getEnrollmentById(@PathVariable Integer id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<EnrollmentResponseDto> cancelEnrollment(@PathVariable Integer id) {
        return ResponseEntity.ok(enrollmentService.cancelEnrollment(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<EnrollmentResponseDto> completeEnrollment(@PathVariable Integer id) {
        return ResponseEntity.ok(enrollmentService.completeEnrollment(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStatus(status));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByEmployeeId(@PathVariable Integer employeeId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByEmployeeId(employeeId));
    }
}
