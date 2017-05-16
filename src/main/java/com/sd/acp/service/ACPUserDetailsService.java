package com.sd.acp.service;

import com.sd.acp.model.User;
import com.sd.acp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*
 * @author    : irfan
 * @Date      : 12-May-17
 * @version   : ver. 1.0.0
 * 
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________ 
 *	
 * 
 * ________________________________________________________________________________________________
 *
 * @Project   : adminportal
 * @Package   : com.sd.acp.service
 * @FileName  : ACPUserDetailsService
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */

@Service
public class ACPUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User name " + username + " not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getGrantedAuthorities(user));
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user.getRole().getName().equals("admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (user.getRole().getName().equals("client")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return grantedAuthorities;
    }
}
