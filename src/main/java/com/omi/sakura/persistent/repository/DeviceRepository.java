package com.omi.sakura.persistent.repository;

import com.omi.sakura.persistent.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    Device findByCodeAndDeleteStatusIs(String code, Integer status);

    @Query(value = "SELECT * FROM devices WHERE daterange_subdiff(return_date,date(:today))<=0 and delete_status=2 ORDER BY return_date DESC", nativeQuery = true)
    List<Device> findDeviceReturnDate(@Param("today") String today);

    @Query(value = "select type_detail,count(code) as num from devices WHERE (status=1 or status=3 )and delete_status=2 GROUP BY type_detail ORDER BY type_detail", nativeQuery = true)
    Object[][] countByType();

    @Query(value = "select * from devices WHERE (status=1 or status=3 ) and delete_status=2 and type_detail=:detail", nativeQuery = true)
    List<Device> findAllByDetailType(@Param("detail") String detailType);

    @Query(value = "select email from staff INNER JOIN company_device on staff.id=company_device.staff_id WHERE  device_id=:id", nativeQuery = true)
    String getEmailByDeviceId(@Param("id") Long id);

    List<Device> findAllByDeleteStatusIs(Integer status);

    Device findByCode(String code);


}
