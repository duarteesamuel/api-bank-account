package com.duarte.bank_account.infrastructure.security;

import com.duarte.bank_account.domain.model.Account;
import com.duarte.bank_account.repositories.AccountRepository;
import com.duarte.bank_account.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoveryToken(request);
        var create = tokenService.validateToken(token);

        if(create != null) {
            Account account = accountRepository.findByEmail(create)
                    .orElseThrow(() -> new RuntimeException("Account not found!"));
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ACCOUNT"));
            var authentication = new UsernamePasswordAuthenticationToken(account, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null){
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}
