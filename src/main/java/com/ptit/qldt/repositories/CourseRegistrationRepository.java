package com.ptit.qldt.repositories;


import com.ptit.qldt.models.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRegistrationRepository  extends JpaRepository<CourseRegistration, Integer> {

}
