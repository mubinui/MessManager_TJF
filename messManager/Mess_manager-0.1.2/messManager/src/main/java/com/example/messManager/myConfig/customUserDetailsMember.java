package com.example.messManager.myConfig;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.messManager.entities.Member;

public class customUserDetailsMember implements UserDetails{
	
	
	private Member member;

	public customUserDetailsMember(Member member) {
		super();
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(member.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean hasRole(String roleName) {
		return member.hasRole(roleName);
	}

}
