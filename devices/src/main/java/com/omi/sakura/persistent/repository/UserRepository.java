package com.omi.sakura.persistent.repository;

import com.omi.sakura.persistent.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserNameIs(String userName);

    List<User> findAllByDeleteStatusIs(Integer status);
}
