package com.omi.sakura.service;

import com.omi.sakura.persistent.domain.User;
import com.omi.sakura.persistent.domain.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("permission")
public class PermissionRole {

    @Autowired
    private UserService userService;

    public boolean hasRole(String role) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = ((UserPrincipal) principal).getUser().getId();
        User user = userService.findById(id);
        if (role.equals(user.getRole())) {
            return true;
        }
        return false;
    }
}