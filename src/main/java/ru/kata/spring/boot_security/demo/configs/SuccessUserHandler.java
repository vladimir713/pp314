package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Collection;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    private RedirectStrategy rs = new DefaultRedirectStrategy();
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
//                httpServletResponse.sendRedirect("/admin");
//                System.out.println("--------------admin------------------------------");
            } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
                hasAdminRole = true;
                break;
//                httpServletResponse.sendRedirect("/user");
//                System.out.println("-------------------------user-------------------------");
//            } else {
//                httpServletResponse.sendRedirect("/");
//                System.out.println("--------------------------null------------------------------");
            }
        }
        if (hasUserRole) {
            rs.sendRedirect(httpServletRequest, httpServletResponse, "/user");
        } else if (hasAdminRole) {
            rs.sendRedirect(httpServletRequest, httpServletResponse, "/admin");
        } else {
            throw new IllegalStateException();
        }
    }
}