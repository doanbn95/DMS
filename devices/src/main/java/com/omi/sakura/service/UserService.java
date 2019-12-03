package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.User;
import com.omi.sakura.request.UserRequest;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void create(UserRequest userForm);

    void delete(Long id);

    UserRequest edit(Long id);

    void update(UserRequest userForm);

    List<User> findAll();

    User findById(Long id);

    boolean checkUserNameExist(String userName, String userNameOld);

    void changePassword(String newPassword);

    User findByMail(String mail);

}
