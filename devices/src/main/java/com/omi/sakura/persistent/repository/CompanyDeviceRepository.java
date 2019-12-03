package com.omi.sakura.persistent.repository;

import com.omi.sakura.persistent.domain.CompanyDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyDeviceRepository extends JpaRepository<CompanyDevice, Long> {
    List<CompanyDevice> findAllByDeleteStatusOrderById(int status);
}
