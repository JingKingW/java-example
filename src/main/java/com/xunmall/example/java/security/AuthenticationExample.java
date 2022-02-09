package com.xunmall.example.java.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gimgoog on 2018/4/28.
 */
public class AuthenticationExample {

    private static AuthenticationManager authenticationManager = new SampleAuthenticationManager();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Please enter your username;");
            String username = reader.readLine();
            System.out.println("Please enter your password;");
            String password = reader.readLine();
            try {
                Authentication request = new UsernamePasswordAuthenticationToken(username, password);
                Authentication result = authenticationManager.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
                break;
            } catch (AuthenticationException ex) {
                System.out.println("Authentication Fail:" + ex.getMessage());
            }
        }
        System.out.println("Successfully authenticated. Security context contains: " +
                SecurityContextHolder.getContext().getAuthentication());

    }

    static class SampleAuthenticationManager implements AuthenticationManager {

        static final List<GrantedAuthority> list = new ArrayList<>();

        static {
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            if (authentication.getName().equals(authentication.getCredentials())) {
                return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), list);
            }
            throw new BadCredentialsException("Bad Credentials");
        }
    }
}
