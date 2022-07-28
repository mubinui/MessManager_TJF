package com.example.messManager.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.messManager.myConfig.customUserDetailsManager;
import com.example.messManager.myConfig.customUserDetailsMember;
 
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
 
	
    
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
    	
    	String redirectURL = request.getContextPath();
    	
    	if(authentication.getPrincipal().toString().contains("customUserDetailsMember")) {
    		customUserDetailsMember member_Details = (customUserDetailsMember) authentication.getPrincipal();
            
        	if (member_Details.hasRole("ROLE_Member")) {
                redirectURL += "member/index/";
            }
    	}else {
    	
    		customUserDetailsManager manager_userDetails = (customUserDetailsManager) authentication.getPrincipal();
    	
    		if (manager_userDetails.hasRole("ROLE_Manager")) {
    			redirectURL += "manager/index/";
    		}
    	}
        
        response.sendRedirect(redirectURL);
         
    }
 
}
