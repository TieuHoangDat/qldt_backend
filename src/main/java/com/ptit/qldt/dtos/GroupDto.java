package com.ptit.qldt.dtos;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Term;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {
    private int groupId;
    private String groupName;
    private Course course;
    private int dayOfWeek;
    private int period;
    private Account teacher;
    private String room;
    private int maxStudents;
    private int availableSlots;
    private Term term;
    private boolean registed;
}
