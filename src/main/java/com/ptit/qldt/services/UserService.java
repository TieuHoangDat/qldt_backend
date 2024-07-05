package com.ptit.qldt.services;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    Account saveUser(Account account);

    Account findByEmail(String email);

    List<AccountDto> findAll();

    Account findByUsername(String username);

    Optional<Account> findById(int id);

    void deleteUser(int id);

    Account findFirstByUsername(String username);

    List<AccountDto> findStudentsByGroupId(int id);

    void updateOtp(int accountId, String otp);

    void updatePassword(int accountId, String newPassword);
}
