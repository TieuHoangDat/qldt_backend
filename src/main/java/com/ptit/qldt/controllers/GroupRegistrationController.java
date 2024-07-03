package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.dtos.TermDto;
import com.ptit.qldt.models.*;
import com.ptit.qldt.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.GroupRegistrationMapper.mapToGroupRegistrationDto;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class GroupRegistrationController {
    private CourseService courseService;
    private GroupRegistrationService groupRegistrationService;
    private GroupService groupService;
    private TermService termService;

    private UserService userService;

    @Autowired
    public GroupRegistrationController(CourseService courseService, GroupRegistrationService groupRegistrationService, GroupService groupService, TermService termService, UserService userService) {
        this.courseService = courseService;
        this.groupRegistrationService = groupRegistrationService;
        this.groupService = groupService;
        this.termService = termService;
        this.userService = userService;
    }




    @GetMapping("/show_grade/{id}")
    ResponseEntity<ResponseObject> updateCourse(@PathVariable("id") int id){

        List<TermDto> listTerm = new ArrayList<>();

        List<Term> li = termService.findAll();

        for(Term term : li) {
            if (term.getId() == 6) continue;
            String name = term.getName();
            List<GroupRegistration> tmp = groupRegistrationService.getCRByIdAndTerm(id, term.getId());
            TermDto t = new TermDto(name, tmp.stream().map(x -> mapToGroupRegistrationDto(x)).collect(Collectors.toList()));

            listTerm.add(t);
        }
        Collections.sort(listTerm);
        double tl_10 = 0, tl_4 = 0;
        int tl = 0;

        for(TermDto t : listTerm) {
            tl_10 += t.getAvg_10() * t.getTotal_credit();
            tl_4 += t.getAvg_4() * t.getTotal_credit();
            tl += t.getTotal_credit();
            t.setTl_10(Math.round((tl_10 / tl)*100.0) / 100.0);
            t.setTl_4(Math.round((tl_4 / tl)*100.0) / 100.0);
            t.setTl_credit(tl);
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Get term successfully", listTerm)
        );
    }


    @GetMapping("/courseRegistration/{id}")
    ResponseListObject listCoursesRegistration(@PathVariable("id") int id) {
        List<CourseDto> li = courseService.findCourseRegister(id);
        return new ResponseListObject("ok", "Query course successfully", li);
    }

    @GetMapping("/groupRegistration/{id}")
    ResponseListObject listGroupsRegistration(@PathVariable("id") int id) {
        List<GroupRegistrationDto> li = groupRegistrationService.findgroupRegistration(id);
        return new ResponseListObject("ok", "Query GR successfully", li);
    }


    @GetMapping("/groupRegistration/groups/{id}")
    ResponseListObject listGroups(@PathVariable("id") int id) {
        List<GroupDto> groups = groupService.findAllGroupInCourseRegistration(id);
        List<GroupRegistrationDto> groupRegistrations = groupRegistrationService.findgroupRegistration(id);
        for(GroupDto g : groups) {
            boolean ok = false;
            for(GroupRegistrationDto gr : groupRegistrations) {
                if (g.getGroupId() == gr.getGroup().getGroupId()) {
                    ok = true;
                    break;
                }
            }
            g.setRegisted(ok);
        }
        return new ResponseListObject("ok", "Query group successfully", groups);
    }

    @GetMapping("/groupRegistration/groups/{id}/{courseId}")
    ResponseListObject listGroupsByCourse(@PathVariable("id") int id, @PathVariable("courseId") String courseId) {
        List<GroupDto> groups = groupService.getGroupsForCourse(courseId);
        List<GroupRegistrationDto> groupRegistrations = groupRegistrationService.findgroupRegistration(id);
        for(GroupDto g : groups) {
            boolean ok = false;
            for(GroupRegistrationDto gr : groupRegistrations) {
                if (g.getGroupId() == gr.getGroup().getGroupId()) {
                    ok = true;
                    break;
                }
            }
            g.setRegisted(ok);
        }
        return new ResponseListObject("ok", "Query group successfully", groups);
    }

    @PostMapping("/groupRegistration/add")
    ResponseEntity<ResponseObject> assignUserToGroup(@RequestBody Map<String, Object> payload) {
        Integer userId = (Integer) payload.get("userId");
        Integer groupId = (Integer) payload.get("groupId");

        List<GroupRegistrationDto> listgr = groupRegistrationService.findgroupRegistration(userId);
        Group group = groupService.getGroupById(groupId);


        // Kiểm tra nhóm còn slot để đăng ký không
        if(group.getAvailableSlots()<=0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Nhóm này đã hết chỗ", "")
            );
        }

        for(GroupRegistrationDto x : listgr) {
            if(group.getGroupId() == x.getGroup().getGroupId()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Bạn đã đăng kí nhóm này", "")
                );
            }
        }

        // Kiểm tra để một môn không được đăng ký 2 nhóm
        for(GroupRegistrationDto x : listgr) {
            if(group.getCourse().getId().equals(x.getGroup().getCourse().getId())) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Một môn học không thể đăng kí 2 nhóm", "")
                );
            }
        }

        
        // Kiểm tra xem có trừng thời khóa biểu không
        for(GroupRegistrationDto x : listgr) {
            if(group.getDayOfWeek() == x.getGroup().getDayOfWeek()
            && group.getPeriod() == x.getGroup().getPeriod()) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Trùng lịch học", "")
                );
            }
        }

        groupRegistrationService.addGroupRegistration(userId, groupId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Đăng kí nhóm thành công", "")
        );
    }

    @DeleteMapping("/groupRegistration/{grId}")
    ResponseEntity<ResponseObject> deleteGroupRegistration(@PathVariable("grId") int id){
        try {
            groupRegistrationService.deleteGroupRegistration(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Hủy đăng kí thành công", "")
            );
        }catch (Exception ex){
            System.out.println("không xóa được");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Có lỗi", "")
            );
        }
    }

    @GetMapping("/time_table/{userId}")
    ResponseEntity<ResponseObject> timeTable(@PathVariable("userId") int userId){
        Optional<Account> acc = userService.findById(userId);
        // Nếu là sinh viên
        if(acc.get().getRole()==3) {
            List<GroupRegistrationDto> ligr = groupRegistrationService.findgroupRegistration(userId);
            List<Group> li = new ArrayList<>();
            for(GroupRegistrationDto gr : ligr) {
                li.add(gr.getGroup());
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query group successfully", li)
            );
        }
        // Nếu là giảng viên
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query group successfully", groupService.getGroupByTeacherID(userId))
        );
    }



}
