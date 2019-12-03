package com.omi.sakura.service;

import com.omi.sakura.common.utils.*;
import com.omi.sakura.persistent.domain.User;
import com.omi.sakura.persistent.domain.UserPrincipal;
import com.omi.sakura.persistent.repository.UserRepository;
import com.omi.sakura.request.MasterRequest;
import com.omi.sakura.request.UserRequest;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private MasterService masterService;

    @Override
    public void create(UserRequest userForm) {
        String generatePassword = GeneratePassword.generatePassword();
        User user = new User();
        user.setUserName(userForm.getUserName());
        user.setPassword(passwordEncoder.encode(generatePassword));
        user.setName(CapitalizeText.convertText(userForm.getName()));
        user.setRole(userForm.getRole());
        user.setDepartment(CapitalizeText.formatText(userForm.getDepartment().trim()));
        user.setCreatePerson(CapitalizeText.convertText(CapitalizeText.formatText(userForm.getCreatePerson())));
        user.setFirstTime(1);
        user.setDeleteStatus(DeleteEnum.False.getStatus());
        if (StringUtils.isEmpty(userForm.getCreateDay())) {
            user.setCreateDay(new Date());
        } else {
            user.setCreateDay(DateUtils.convertToDate(userForm.getCreateDay()));
        }
        //Master
        MasterRequest masterRequest = MasterRequest.builder()
                .type(MasterTypeEnum.BOPHAN.getCode())
                .value(userForm.getDepartment().trim())
                .build();
        masterService.create(masterRequest);
        userRepository.save(user);

        mailService.sendMail(generatePassword, userForm.getUserName(), Constants.CREATE_PASSWORD);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }

    @Override
    public UserRequest edit(Long id) {
        UserRequest userForm = new UserRequest();
        User user = userRepository.findOne(id);
        userForm.setId(user.getId());
        userForm.setUserName(user.getUserName());
        userForm.setName(user.getName());
        userForm.setRole(user.getRole());
        userForm.setDepartment(user.getDepartment());
        userForm.setCreatePerson(user.getCreatePerson());
        if (!StringUtils.isEmpty(user.getCreateDay())) {
            userForm.setCreateDay(DateUtils.convertDateToString(user.getCreateDay()));
        }
        userForm.setUserNameOld(user.getUserName());
        return userForm;
    }

    @Override
    public void update(UserRequest userForm) {
        User user = userRepository.findOne(userForm.getId());
        user.setUserName(userForm.getUserName());
        user.setName(CapitalizeText.convertText(userForm.getName()));
        user.setRole(userForm.getRole());
        user.setDepartment(CapitalizeText.formatText(userForm.getDepartment().trim()));
        user.setCreatePerson(CapitalizeText.convertText(userForm.getCreatePerson()));
        if (!StringUtils.isEmpty(userForm.getCreateDay())) {
            user.setCreateDay(DateUtils.convertToDate(userForm.getCreateDay()));
        }
        MasterRequest masterRequest = MasterRequest.builder()
                .type(MasterTypeEnum.BOPHAN.getCode())
                .value(userForm.getDepartment().trim())
                .build();
        masterService.create(masterRequest);
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAllByDeleteStatusIs(DeleteEnum.False.getStatus());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public boolean checkUserNameExist(String userName, String userNameOld) {

        if (!StringUtils.isEmpty(userName)) {
            User flag = userRepository.findByUserNameIs(userName);
            if (userNameOld == null) {
                return flag == null;
            } else {
                return userName.equals(userNameOld) || flag == null;
            }
        }
        return true;

    }

    @Override
    public void changePassword(String newPassword) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = ((UserPrincipal) principal).getUser().getId();
        User user = userRepository.findOne(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setFirstTime(2);
        userRepository.save(user);
    }

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserNameIs(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown User");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new UserPrincipal(
                user, true, true, true, true,
                grantedAuthorities);

    }

    @Override
    public User findByMail(String mail) {
        return userRepository.findByUserNameIs(mail);
    }
}