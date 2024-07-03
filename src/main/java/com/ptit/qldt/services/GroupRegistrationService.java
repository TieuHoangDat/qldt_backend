package com.ptit.qldt.services;

import com.ptit.qldt.dtos.GroupDto;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.GroupRegistration;

import java.util.List;

public interface GroupRegistrationService {

    List<GroupRegistration> getCRByIdAndTerm(int accountId, int id);

    List<GroupRegistrationDto> findgroupRegistration(int accountId);

    void addGroupRegistration(int accountId, int groupId);

    void deleteGroupRegistration(int id);

//    List<GroupRegistrationDto> findGroupByDayOfWeekAndTime(String dayOfWeek, String time);
}
