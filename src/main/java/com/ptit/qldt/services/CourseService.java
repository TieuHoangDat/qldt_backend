package com.ptit.qldt.services;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.models.Course;

import java.util.List;

public interface CourseService {
    List<CourseDto> findAllCourse();
    List<CourseDto> findCourseRegister(int accountId);
    Course saveCourse(Course course);

    void delete(String courseId);

}
