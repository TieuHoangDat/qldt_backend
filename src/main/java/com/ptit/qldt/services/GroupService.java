package com.ptit.qldt.services;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Group;

import java.util.List;

public interface GroupService {

    List<GroupDto> findAllGroupInCourseRegistration(int accountId);

    List<Group> getGroupByTeacherID(int accountId);

    List<GroupDto> getGroupsForCourse(String courseId);

    Group getGroupById(int groupId);

    Group saveGroup(Group group);

    void delete(int groupId);


}
