package com.ptit.qldt.services.impl;

import com.ptit.qldt.dtos.AccountDto;
import com.ptit.qldt.dtos.RegistrationDto;
import com.ptit.qldt.models.Account;
import com.ptit.qldt.models.Term;
import com.ptit.qldt.repositories.TermRepository;
import com.ptit.qldt.repositories.UserRepository;
import com.ptit.qldt.services.TermService;
import com.ptit.qldt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ptit.qldt.mappers.AccountMapper.mapToAccountDto;

@Service
public class TermServiceImpl implements TermService {
    private TermRepository termRepository;
    @Autowired
    public TermServiceImpl(TermRepository termRepository) {
        this.termRepository = termRepository;
    }


    @Override
    public List<Term> findAll() {
        List<Term> li = termRepository.findAll();
        return li;
    }


}
