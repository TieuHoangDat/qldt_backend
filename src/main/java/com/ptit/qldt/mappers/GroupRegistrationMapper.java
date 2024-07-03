package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.GroupRegistration;

public class GroupRegistrationMapper {
    public static GroupRegistrationDto mapToGroupRegistrationDto(GroupRegistration groupRegistration) {
        double grade_4, grade_10;
        String grade_alpha;
        grade_10 = Math.round(groupRegistration.getGrade()*10.0) / 10.0;
        if(grade_10 < 4) {
            grade_alpha = "F";
            grade_4 = 0;
        }
        else if(grade_10 < 5) {
            grade_alpha = "D";
            grade_4 = 1;
        }
        else if(grade_10 < 5.5) {
            grade_alpha = "D+";
            grade_4 = 1.5;
        }
        else if(grade_10 < 6.5) {
            grade_alpha = "C";
            grade_4 = 2;
        }
        else if(grade_10 < 7) {
            grade_alpha = "C+";
            grade_4 = 2.5;
        }
        else if(grade_10 < 8) {
            grade_alpha = "B";
            grade_4 = 3;
        }
        else if(grade_10 < 8.5) {
            grade_alpha = "B+";
            grade_4 = 3.5;
        }
        else if(grade_10 < 9) {
            grade_alpha = "A";
            grade_4 = 3.7;
        }
        else {
            grade_alpha = "A+";
            grade_4 = 4.0;
        }
        return GroupRegistrationDto.builder()
                .id(groupRegistration.getId())
                .group(groupRegistration.getGroup())
                .account(groupRegistration.getAccount())
                .time(groupRegistration.getTime())
                .grade_10(groupRegistration.getGrade())
                .grade_4(grade_4)
                .grade_a(grade_alpha).build();
    }
}
