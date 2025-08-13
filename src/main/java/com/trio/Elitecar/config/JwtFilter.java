package com.trio.Elitecar.config;

import com.trio.Elitecar.service.JwtService;
import com.trio.Elitecar.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
////token validation.
////extends OncePerRequestFilter which means it call every request per once.
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //holding the token details
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;


        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            //after extracting the username
            userName = jwtService.extractUserName(token);
        }

        //if username is not equal to null and auth is null then it checks
        if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(userName);


            if(jwtService.validateToken(token, userDetails)){
                //new authtoken is created and given to the securitycontextholder so that it will check whether the token is available.
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("User authenticated: " + userName);
            }
        }
        //request goes to next filter which is usernamepasswordAuthentication
        filterChain.doFilter(request, response);
    }
}
