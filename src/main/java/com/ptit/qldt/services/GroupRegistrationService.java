package com.ptit.qldt.services;
import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.GroupRegistration;

import java.util.List;

public interface GroupRegistrationService {

    List<GroupRegistration> getGRByIdAndTerm(int accountId, int id);

    List<GroupRegistration> getGRByGroupId(int groupId);

    List<GroupRegistrationDto> findgroupRegistration(int accountId);

    void addGroupRegistration(int accountId, int groupId);

    void deleteGroupRegistration(int id);

    void updateGroupRegistration(GroupRegistration gr);

}
