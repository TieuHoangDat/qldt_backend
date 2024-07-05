package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.dtos.TermDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.CourseRegistration;
import com.ptit.qldt.models.ResponseListObject;
import com.ptit.qldt.services.CourseRegistrationService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.CourseRegistrationMapper.mapToCourseRegistrationDto;

@Controller
public class CourseRegistrationController {
    private CourseRegistrationService courseRegistrationService;
    @Autowired
    public CourseRegistrationController(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }


}
