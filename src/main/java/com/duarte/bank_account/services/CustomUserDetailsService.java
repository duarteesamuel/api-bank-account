package com.duarte.bank_account.services;

import com.duarte.bank_account.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.duarte.bank_account.domain.model.Account;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found!"));

        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                new ArrayList<>()
        );
    }
}
