package com.omi.sakura.persistent.repository;

import com.omi.sakura.persistent.domain.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {

    List<Master> findByTypeOrderByIdAsc(int Type);

}
