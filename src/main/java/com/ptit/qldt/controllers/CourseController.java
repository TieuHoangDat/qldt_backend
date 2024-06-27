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
                    new ResponseObject("false", "False", "")
            );
        }
    }


    @PostMapping("/filter")
    public String filterCourseBySemester(Model model,@RequestParam(value = "semester") String semester){
        Course course = new Course();
        List<CourseDto> courses = new ArrayList<>();
        if(semester.equals("Tất cả")){
            courses = courseService.findAllCourse();
        }else {
            courses = courseService.findCourseBySemester(Integer.parseInt(semester));
        }
        model.addAttribute("semester" , semester);
        model.addAttribute("courses", courses);
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }

    @GetMapping("/courses/term-{term}")
    public String filterCourse(@PathVariable("term") String term,Model model){
        Course course = new Course();
        List<CourseDto> courses = new ArrayList<>();
        if(term.equals("Tất cả")){
            courses = courseService.findAllCourse();
        }else {
            courses = courseService.findCourseBySemester(Integer.parseInt(term));
        }
        model.addAttribute("courses", courses);
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }
    @GetMapping("/courses/new")
    public String createCourse(Model model){
        Course course = new Course();
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }







    @GetMapping("/courses/{courseId}/delete")
    public String deleteCourse(@PathVariable("courseId") String courseId,Model model){
        try {
            courseService.delete(courseId);
            return "redirect:/courses";
        }catch (Exception ex){
            System.out.println("không xóa được");
//            model.addAttribute("notaction","block");
        }
        Course course = new Course();
        String a = "Tất cả";
        List<CourseDto> courses = courseService.findAllCourse();
        model.addAttribute("courses", courses);
        model.addAttribute("course",course);
        model.addAttribute("term", a);
        model.addAttribute("notaction","block");
        model.addAttribute("courseactive","active");
        return "course_manager";
    }

    @GetMapping("/search")
    public String listCourseSearch(@RequestParam(value = "searchCourse") String name , Model model){
        List<CourseDto> courses = courseService.findCourseByName(name);
        Course course = new Course();
        model.addAttribute("courses",courses);
        model.addAttribute("course",course);
        model.addAttribute("courseactive","active");
        return "course_manager";
    }

}
