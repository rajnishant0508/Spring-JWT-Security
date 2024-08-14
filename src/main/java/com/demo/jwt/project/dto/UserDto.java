package com.demo.jwt.project.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails{

	    private String email;

	    private String password;
	    
	    private List<RoleDto> role;

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			var authorities = role
					.stream()
					.flatMap(role -> role.getPermissions().stream())
					.map(permission -> new SimpleGrantedAuthority(permission.getAuthorityType()))
					.collect(Collectors.toList());
			for(RoleDto role : role) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleType()));
			}
			
			System.out.println(authorities);
			return authorities;
		}

		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return email;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return false;
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
}
