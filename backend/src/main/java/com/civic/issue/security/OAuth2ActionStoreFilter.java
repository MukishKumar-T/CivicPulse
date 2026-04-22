package com.civic.issue.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that captures the 'action' query param (login vs register)
 * from the initial /oauth2/authorization/google call and stores it
 * in the session for the UserService to pick up later.
 */
@Component
public class OAuth2ActionStoreFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("🔥 Filter Hit URI: " + request.getRequestURI());
        System.out.println("🔥 Action Param: " + request.getParameter("action"));

        if (request.getRequestURI().contains("/oauth2/authorization/")) {
            String action = request.getParameter("action");
            if (action != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("oauth_action", action);

                System.out.println("✅ Stored in session: " + action);
                System.out.println("🧠 Session ID: " + session.getId());
            }
        }

        filterChain.doFilter(request, response);
    }
}
