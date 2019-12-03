package com.omi.sakura.persistent.repository;

import com.omi.sakura.persistent.domain.IndividualDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndividualDeviceRepository extends JpaRepository<IndividualDevice, Long> {
    IndividualDevice findByCodeDevice(String code);

    List<IndividualDevice> findAllByDeleteStatus(int status);

}
