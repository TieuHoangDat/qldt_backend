package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.NotificationDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Notification;
import com.ptit.qldt.models.ResponseListObject;
import com.ptit.qldt.models.ResponseObject;
import com.ptit.qldt.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static com.ptit.qldt.mappers.NotificationMapper.mapToNotificationDto;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class HomeController {
    private NotificationService notificationService;
    @Autowired
    public HomeController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    ResponseListObject listNotification() {
        List<NotificationDto> li = notificationService.findAllNotification();
        return new ResponseListObject("ok", "Query notification successfully", li);
    }

    @PostMapping("/notifications")
    ResponseEntity<ResponseObject> saveNotification(@RequestBody Notification newNotification){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Create notification successfully", notificationService.save(newNotification))
        );
    }

    @PutMapping("/notifications/{notificationId}")
    ResponseEntity<ResponseObject> updateNotification(@PathVariable("notificationId") int notificationId, @RequestBody Notification newNotification){
        newNotification.setId(notificationId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update notification successfully", notificationService.save(newNotification))
        );
    }

    @DeleteMapping("/notifications/{notificationId}")
    ResponseEntity<ResponseObject> deleteNotification(@PathVariable("notificationId") int notificationId){
        try {
            notificationService.deleteNotificationById(notificationId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete notification successfully", "")
            );
        }catch (Exception ex){
            System.out.println("không xóa được");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Không thể xóa thông học này", "")
            );
        }
    }

    @GetMapping("/home/{notificationId}/delete")
    public String deleteNotification(@PathVariable int notificationId,Model model) {
        notificationService.deleteNotificationById(notificationId);
        model.addAttribute("homeactive","active");
        return "redirect:/home";
    }

    @GetMapping("/home/{notificationId}/edit")
    public String editNotification(Model model, @PathVariable int notificationId) {
        List<NotificationDto> notifications = notificationService.findAllNotification();
        model.addAttribute("notifications", notifications);
        model.addAttribute("blockEdit", true);
        NotificationDto notificationDto = notificationService.findById(notificationId);
        model.addAttribute("notification", notificationDto);
        model.addAttribute("homeactive","active");
        return "home";
    }

    @GetMapping("/home/{notificationId}/detail")
    public String showNotification(Model model, @PathVariable int notificationId) {
        NotificationDto notificationDto = notificationService.findById(notificationId);
        model.addAttribute("notification", notificationDto);
        model.addAttribute("homeactive","active");
        return "notification";
    }

    @PostMapping("/home/{notificationId}/edit")
    public String updateNotification(Model model,@PathVariable int notificationId, @ModelAttribute("notification") NotificationDto notificationDto) {
        notificationDto.setId(notificationId);
        notificationService.updateNotification(notificationDto);
        model.addAttribute("homeactive","active");
        return "redirect:/home";
    }
}
