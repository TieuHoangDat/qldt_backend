package com.ptit.qldt.mappers;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.models.Account;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account) {
        AccountDto accountDto = AccountDto.builder()
                .account_id(account.getAccount_id())
                .name(account.getName())
                .username(account.getUsername())
                .email(account.getEmail())
                .gender(account.getGender())
                .date(account.getDate())
                .month(account.getMonth())
                .year(account.getYear())
                .role(account.getRole())
                .build();
        return accountDto;
    }
}
