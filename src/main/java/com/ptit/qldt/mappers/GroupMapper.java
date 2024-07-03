package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.CourseDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.Group;

public class GroupMapper {
    public static GroupDto mapToGroupDto(Group group) {
        GroupDto groupDto = GroupDto.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .course(group.getCourse())
                .room(group.getRoom())
                .availableSlots(group.getAvailableSlots())
                .maxStudents(group.getMaxStudents())
                .teacher(group.getTeacher())
                .dayOfWeek(group.getDayOfWeek())
                .period(group.getPeriod())
                .term(group.getTerm())
                .build();
        return groupDto;
    }

    public static Group mapToGroup(GroupDto groupDto) {
        Group group = Group.builder().groupId(groupDto.getGroupId())
                .groupName(groupDto.getGroupName())
                .course(groupDto.getCourse())
                .dayOfWeek(groupDto.getDayOfWeek())
                .period(groupDto.getPeriod())
                .teacher(groupDto.getTeacher())
                .room(groupDto.getRoom())
                .maxStudents(groupDto.getMaxStudents())
                .availableSlots(groupDto.getAvailableSlots())
                .term(groupDto.getTerm())
                .build();

        return group;
    }
}
