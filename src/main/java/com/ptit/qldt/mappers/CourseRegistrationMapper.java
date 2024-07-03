package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.CourseRegistration;

public class CourseRegistrationMapper {
    public static CourseRegistrationDto mapToCourseRegistrationDto(CourseRegistration courseRegistration) {

        CourseRegistrationDto courseRegistrationDto = CourseRegistrationDto.builder()
                .id(courseRegistration.getId())
                .account(courseRegistration.getAccount())
                .course(courseRegistration.getCourse())
                .term(courseRegistration.getTerm())
                .registrationDate(courseRegistration.getRegistrationDate())
                .build();
        return courseRegistrationDto;
    }

    public static CourseRegistration mapToCourseRegistration(CourseRegistrationDto courseRegistrationDto){
        CourseRegistration courseRegistration = CourseRegistration.builder()
                .id(courseRegistrationDto.getId())
                .account(courseRegistrationDto.getAccount())
                .course(courseRegistrationDto.getCourse())
                .term(courseRegistrationDto.getTerm())
                .registrationDate(courseRegistrationDto.getRegistrationDate())
                .build();
        return courseRegistration;
    }
}
