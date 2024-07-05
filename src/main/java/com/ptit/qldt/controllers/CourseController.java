package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.ResponseListObject;
import com.ptit.qldt.models.ResponseObject;
import com.ptit.qldt.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    ResponseListObject listCourses() {
        List<CourseDto> li = courseService.findAllCourse();
        li.sort(Comparator.comparing(CourseDto::getTerm));
        return new ResponseListObject("ok", "Query course successfully", li);
    }

    @PostMapping("/courses")
    ResponseEntity<ResponseObject> saveCourse(@RequestBody Course newCourse){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Create course successfully", courseService.saveCourse(newCourse))
        );
    }

    @PutMapping("/courses/{courseId}")
    ResponseEntity<ResponseObject> updateCourse(@PathVariable("courseId") String courseId, @RequestBody Course newCourse){
        newCourse.setId(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update course successfully", courseService.saveCourse(newCourse))
        );
    }

    @DeleteMapping("/courses/{courseId}")
    ResponseEntity<ResponseObject> deleteCourse(@PathVariable("courseId") String courseId){
        try {
            courseService.delete(courseId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete course successfully", "")
            );
        }catch (Exception ex){
            System.out.println("không xóa được");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Không thể xóa môn học này", "")
            );
        }
    }


}
