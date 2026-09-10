package com.hdfc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CourseRequestDto {
	
	@NotBlank(message = "Course name cannot be blank")
    private String courseName;

    @NotBlank(message = "Trainer name cannot be blank")
    private String trainerName;

    @NotNull(message = "Duration is required")
    @Positive(message = "Course duration must be greater than zero")
    private Integer durationInDays;

    @NotNull(message = "Max capacity is required")
    @Positive(message = "Max capacity must be a positive number")
    private Integer maxCapacity;

    @NotNull(message = "Fees are required")
    @Positive(message = "Course fees must always be positive")
    private Double fees;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public Integer getDurationInDays() {
		return durationInDays;
	}

	public void setDurationInDays(Integer durationInDays) {
		this.durationInDays = durationInDays;
	}

	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}
    
    
    
    
    
    

}
