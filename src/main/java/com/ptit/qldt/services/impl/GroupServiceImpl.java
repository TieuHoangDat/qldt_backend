package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.CourseRegistrationDto;
import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Course;
import com.ptit.qldt.models.CourseRegistration;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.repositories.AccountRepository;
import com.ptit.qldt.repositories.CourseRegistrationRepository;
import com.ptit.qldt.repositories.CourseRepository;
import com.ptit.qldt.repositories.GroupRepository;
import com.ptit.qldt.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.ptit.qldt.mappers.AccountMapper.mapToAccountDto;
import static com.ptit.qldt.mappers.CourseRegistrationMapper.mapToCourseRegistration;
import static com.ptit.qldt.mappers.CourseRegistrationMapper.mapToCourseRegistrationDto;
import static com.ptit.qldt.mappers.GroupMapper.mapToGroup;
import static com.ptit.qldt.mappers.GroupMapper.mapToGroupDto;

@Service
public class GroupServiceImpl implements GroupService {
    private CourseRepository courseRepository;
    private GroupRepository groupRepository;
    private CourseRegistrationRepository courseRegistrationRepository;
    private AccountRepository accountRepository;
    private GroupService groupService;
    @Autowired
    public GroupServiceImpl(CourseRepository courseRepository,
                            GroupRepository groupRepository,
                            CourseRegistrationRepository courseRegistrationRepository,
                            AccountRepository accountRepository) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
        this.accountRepository = accountRepository;

    }
    @Override
    public List<GroupDto> findAllGroupInCourseRegistration(int accountId) {
        List<Course> courses = courseRepository.findCourseRegister(accountId);
        List<Group> groups = groupRepository.findGroupsByCourses(courses);
        return groups.stream().map(group -> mapToGroupDto(group)).collect(Collectors.toList());
    }

    @Override
    public List<Group> getGroupByTeacherID(int accountId) {
        return groupRepository.getGroupByTeacherID(accountId);
    }

    @Override
    public List<GroupDto> getGroupsForCourse(String courseId) {
        List<Group> groups = groupRepository.findByCourseId(courseId);
        return groups.stream().map(group -> mapToGroupDto(group)).collect(Collectors.toList());
    }

    @Override
    public List<GroupDto> findAllGroup() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(group -> mapToGroupDto(group)).collect(Collectors.toList());
    }
    @Override
    public Group getGroupById(int groupId) {
        return groupRepository.findGroupById(groupId);
    }

    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<AccountDto> getAccountByCourseId(String courseId) {
        return null;
    }

    @Override
    public GroupDto findGroupById(int groupId) {
        Group group = groupRepository.findById(groupId).get();
        return mapToGroupDto(group);
    }

    @Override
    public void updateGroup(GroupDto groupDto) {
        Group group = mapToGroup(groupDto);
        groupRepository.save(group);
    }

    @Override
    public void delete(int groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public List<CourseRegistrationDto> readExcel(String filePath, String courseId) {
        return null;
    }

    @Override
    public CourseRegistrationDto findCourseRegistration(String courseId, int accountId) {
        CourseRegistration courseRegistration = courseRepository.findCourseRegisterByCourseIdAndAccountId(courseId,accountId);
        return mapToCourseRegistrationDto(courseRegistration);
    }

    @Override
    public void updateCourseRigistation(CourseRegistrationDto courseRegistrationDto) {
        CourseRegistration courseRegistration = mapToCourseRegistration(courseRegistrationDto);
        courseRegistrationRepository.save(courseRegistration);
    }

    @Override
    public List<AccountDto> findAllTeacherAccount() {
        List<Account> accounts = accountRepository.findAllTeacher();
        return accounts.stream().map(account -> mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> findAllStudentAccount() {
        List<Account> accounts = accountRepository.findAllStudent();
        return accounts.stream().map(account -> mapToAccountDto(account)).collect(Collectors.toList());
    }
}
