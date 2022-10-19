package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    final private RedirectStrategy rs = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse
            , Authentication authentication) throws IOException {
        boolean hasUserRole = false;
        boolean hasAdminRole = false;
        Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : roles) {
            if (grantedAuthority.getAuthority().equals("USER")) {
                hasUserRole = true;
                break;
            } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
                hasAdminRole = true;
                break;
            }
        }
        if (hasUserRole) {
            rs.sendRedirect(httpServletRequest, httpServletResponse, "/user");
            System.out.println("suh - /user");
        } else if (hasAdminRole) {
            rs.sendRedirect(httpServletRequest, httpServletResponse, "/admin");
            System.out.println("suh - /admin");
        } else {
            rs.sendRedirect(httpServletRequest, httpServletResponse, "/login");
            System.out.println("suh - /");
//            throw new IllegalStateException();
        }
    }
}