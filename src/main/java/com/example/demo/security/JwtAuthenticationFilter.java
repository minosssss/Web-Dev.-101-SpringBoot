package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // 서블릿 필터 구현
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 요청에서 token 가져오기
            String token = parseBearerToken(req);
            log.info("Filter is running...");

            // 토근 검사하기. jwt이므로 인가 서버에 요청하지 않고도 검증 가능
            if (token != null && !token.equalsIgnoreCase("null")) {
                // userid 가져오기, 위조된 경우 예외
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authenticated user ID: " + userId);
                // 인증완료.
                AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                userId,
                        null,
                        AuthorityUtils.NO_AUTHORITIES);
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(auth);
                SecurityContextHolder.setContext(securityContext);

            }

        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }
        filterChain.doFilter(req,res);
    }

    private String parseBearerToken(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
