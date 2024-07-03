package com.ptit.qldt.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private int account_id;
    private String name;
    private String username;
    private String email;
    private String gender;
    private Integer date;
    private Integer month;
    private Integer year;
    private Integer role;
}
