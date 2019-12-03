package com.omi.sakura.persistent.repository;

import com.omi.sakura.persistent.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {


    Staff findByEmail(String email);
}
