package com.ptit.qldt.repositories;

import com.ptit.qldt.models.GroupRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRegistrationRepository  extends JpaRepository<GroupRegistration, Integer> {

    @Query("SELECT gr FROM GroupRegistration gr WHERE gr.account.account_id = :accountId AND gr.group.course.term = 6")
    List<GroupRegistration> findGroupRegistration(@Param("accountId") int accountId);

    @Query("SELECT gr FROM GroupRegistration gr WHERE gr.account.account_id = :accountId AND gr.group.term.id = :term")
    List<GroupRegistration> findGRByIdAndTerm(int accountId, int term);

    @Query("SELECT gr FROM GroupRegistration gr WHERE gr.group.groupId = :groupId")
    List<GroupRegistration> findGRByGroupId(int groupId);
}
