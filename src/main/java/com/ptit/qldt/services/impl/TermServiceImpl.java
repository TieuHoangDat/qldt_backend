package com.ptit.qldt.services.impl;

import com.ptit.qldt.models.Term;
import com.ptit.qldt.repositories.TermRepository;
import com.ptit.qldt.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
