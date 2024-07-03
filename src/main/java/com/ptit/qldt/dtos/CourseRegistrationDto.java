package com.ptit.qldt.dtos;

import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Term;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
@Data
@Builder
public class CourseRegistrationDto {
    private Long id;
    private Account account;
    private Course course;
    private Term term;
    private LocalDateTime registrationDate;

}
