package com.project.wsda.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    SimpleUrlAuthenticationSuccessHandler shopSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/shop");
    SimpleUrlAuthenticationSuccessHandler managerSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/manager");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("manager")) {
                this.managerSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }
        }
        this.shopSuccessHandler.onAuthenticationSuccess(request, response, authentication);
    }
}
