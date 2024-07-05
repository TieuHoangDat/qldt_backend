package com.ptit.qldt.controllers;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.NotificationDto;
import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.ResponseListObject;
import com.ptit.qldt.models.ResponseObject;
import com.ptit.qldt.services.EmailService;
import com.ptit.qldt.services.NotificationService;
import com.ptit.qldt.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthController {
    private UserService userService;
    private EmailService emailService;
    private NotificationService notificationService;

    public AuthController(UserService userService, EmailService emailService,NotificationService notificationService) {
        this.userService = userService;
        this.emailService = emailService;
        this.notificationService = notificationService;
    }

    @GetMapping("/users")
    ResponseListObject listUser() {
        List<AccountDto> li = userService.findAll();
        return new ResponseListObject("ok", "Query user successfully", li);
    }

    @PostMapping("/users")
    ResponseEntity<ResponseObject> saveUser(@RequestBody Account newAccount){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Create account successfully", userService.saveUser(newAccount))
        );
    }

    @PutMapping("/users/{userId}")
    ResponseEntity<ResponseObject> updateUser(@PathVariable("userId") int userId, @RequestBody Account newAccount){
        Account a = userService.findFirstByUsername(newAccount.getUsername());
        newAccount.setPassword(a.getPassword());
        newAccount.setAccount_id(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update user successfully", userService.saveUser(newAccount))
        );
    }

    @DeleteMapping("/users/{userId}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable("userId") int userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete user successfully", "")
            );
        }catch (Exception ex){
            System.out.println("không xóa được");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("false", "Không thể xóa user này", "")
            );
        }
    }


    @PostMapping("/login")
    ResponseEntity<ResponseObject> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        Account user = userService.findFirstByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            String token = user.generateAuthToken();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Đăng nhập thành công", token)
            );
        }
        return ResponseEntity.status(400).body(
                new ResponseObject("ok", "Tải khoản hoặc mật khẩu không đúng", "")
        );
    }


    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult result, Model model) {
        Account existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }
        Account existingUserUsername = userService.findByUsername(user.getUsername());
        if(existingUserUsername != null && existingUserUsername.getUsername() != null && !existingUserUsername.getUsername().isEmpty()) {
            return "redirect:/register?fail";
        }
        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/courses?success";
    }




    @GetMapping("/forget-password")
    public String forgetPasswordPage(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "forget_password";
    }

    public static String generateOTP() {
        // Mảng các ký tự cho mã OTP
        String chars = "0123456789";
        StringBuilder otp = new StringBuilder();

        // Sinh mã OTP ngẫu nhiên
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            otp.append(chars.charAt(random.nextInt(chars.length())));
        }

        return otp.toString();
    }

    @PostMapping("/send-otp")
    public String sendOtp(HttpSession session, @ModelAttribute("user")RegistrationDto acc) {
        String username = acc.getUsername();
        String email = acc.getEmail();

        Account user = userService.findFirstByUsername(username);

        if (user != null && user.getEmail().equals(email)) {
            // tao, luu, gui otp
            String otp = generateOTP();
            userService.updateOtp(user.getAccount_id(), otp);
            emailService.sendSimpleMessage(user.getEmail(), "Reset password", otp);

            return "redirect:/reset-password";
        } else {
            return "redirect:/forget-password?fail";
        }
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "reset_password";
    }
    @PostMapping("/reset-password/check")
    public String reset(HttpSession session, @ModelAttribute("user")RegistrationDto acc) {
        String otp = acc.getOtp();
        String username = acc.getUsername();
        String newPassword = acc.getPassword();

        Account user = userService.findFirstByUsername(username);

        if (otp!=null && user != null && otp.equals(user.getOtp())) {
            // update password
            userService.updatePassword(user.getAccount_id(), newPassword);

            session.setAttribute("acc", user);
            return "redirect:/courses";
        } else {
            return "redirect:/reset-password?fail";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("acc");
        session.removeAttribute("allNotification");
        return "redirect:/login";
    }

}
