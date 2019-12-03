package com.omi.sakura.persistent.repository;

import com.omi.sakura.persistent.domain.CustomerDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerDeviceRepository extends JpaRepository<CustomerDevice, Long> {
    List<CustomerDevice> findByDeleteStatusIs(int deleteStatus);
}