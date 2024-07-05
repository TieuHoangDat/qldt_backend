package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.models.ResponseObject;
import com.ptit.qldt.services.GroupService;
import com.ptit.qldt.services.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class GroupController {
    private GroupService groupService;
    private UserService userService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }


    @GetMapping("/groupByCourse/{courseId}")
    ResponseEntity<ResponseObject> showGroupByCourse(@PathVariable("courseId") String courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query group successfully", groupService.getGroupsForCourse(courseId))
        );
    }

    @GetMapping("/getGroups/{groupId}")
    ResponseEntity<ResponseObject> getGroup(@PathVariable("groupId") int groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query group successfully", groupService.getGroupById(groupId))
        );
    }

    @PostMapping("/groups")
    ResponseEntity<ResponseObject> saveGroup(@RequestBody Group newGroup) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Create group successfully", groupService.saveGroup(newGroup))
        );
    }

    @PutMapping("/groups/{groupId}")
    ResponseEntity<ResponseObject> updateGroup(@PathVariable("groupId") int groupId, @RequestBody Group newGroup) {
        newGroup.setGroupId(groupId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update group successfully", groupService.saveGroup(newGroup))
        );
    }

    @DeleteMapping("/groups/{groupId}")
    ResponseEntity<ResponseObject> deleteGroup(@PathVariable("groupId") int groupId) {
        try {
            groupService.delete(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete group successfully", "")
            );
        } catch (Exception ex) {
            System.out.println("không xóa được");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Không thể xóa nhóm học này", "")
            );
        }
    }

    @GetMapping("/getListStudent/{groupId}")
    ResponseEntity<ResponseObject> getListStudent(@PathVariable("groupId") int groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query student successfully", userService.findStudentsByGroupId(groupId))
        );
    }



}
