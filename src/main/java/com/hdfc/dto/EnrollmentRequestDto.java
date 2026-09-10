package com.hdfc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnrollmentRequestDto {
	
	@NotNull(message = "Employee ID cannot be null")
    private Integer employeeId;

    @NotBlank(message = "Employee name cannot be blank")
    private String employeeName;

    @NotNull(message = "Course ID cannot be null")
    private Integer courseId;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
    
    
    

}
