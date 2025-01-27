package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.GroupRegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Group;
import com.ptit.qldt.models.GroupRegistration;
import com.ptit.qldt.repositories.GroupRegistrationRepository;
import com.ptit.qldt.repositories.GroupRepository;
import com.ptit.qldt.services.GroupRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static com.ptit.qldt.mappers.GroupRegistrationMapper.mapToGroupRegistrationDto;

@Service
public class GroupRegistrationServiceImpl implements GroupRegistrationService {
    private GroupRegistrationRepository groupRegistrationRepository;
    private GroupRepository groupRepository;


    @Autowired
    public GroupRegistrationServiceImpl(GroupRegistrationRepository groupRegistrationRepository, GroupRepository groupRepository) {
        this.groupRegistrationRepository = groupRegistrationRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<GroupRegistration> getGRByIdAndTerm(int accountId, int id) {
        return groupRegistrationRepository.findGRByIdAndTerm(accountId, id);
    }

    @Override
    public List<GroupRegistration> getGRByGroupId(int groupId) {
        return groupRegistrationRepository.findGRByGroupId(groupId);
    }

    @Override
    public List<GroupRegistrationDto> findgroupRegistration(int accountId) {
        List<GroupRegistration> groupRegistrations = groupRegistrationRepository.findGroupRegistration(accountId);
        return groupRegistrations.stream().map(groupRegistration -> mapToGroupRegistrationDto(groupRegistration)).collect(Collectors.toList());
    }

    @Override
    public void addGroupRegistration(int accountId, int groupId) {
        Account account = new Account();
        account.setAccount_id(accountId);

        Group group = new Group();
        group.setGroupId(groupId);

        GroupRegistration groupRegistration = new GroupRegistration();
        groupRegistration.setAccount(account);
        groupRegistration.setGroup(group);

        // Lưu đối tượng GroupRegistration vào cơ sở dữ liệu
        groupRegistrationRepository.save(groupRegistration);
        groupRepository.decreaseAvailableSlots(groupId);
    }

    @Override
    public void deleteGroupRegistration(int id) {
        GroupRegistration gr = groupRegistrationRepository.findById(id).get();
        groupRegistrationRepository.deleteById(id);
        groupRepository.increaseAvailableSlots(gr.getGroup().getGroupId());
    }

    @Override
    public void updateGroupRegistration(GroupRegistration gr) {
        groupRegistrationRepository.save(gr);
    }


}
