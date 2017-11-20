package com.techmaster.hunter.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HunterAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	

    private final ObjectMapper mapper;

    @Autowired
    HunterAuthSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
        this.mapper = messageConverter.getObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
    	response.setStatus(HttpServletResponse.SC_OK);

        HunterUserDetailService userDetails = (HunterUserDetailService) authentication.getPrincipal();
        User user = (User)userDetails.getUser();
        userDetails.setUser(user);

        System.out.println(user.getUsername() + " got is connected ");

        PrintWriter writer = response.getWriter();
        mapper.writeValue(writer, user);
        writer.flush();
    }

}
