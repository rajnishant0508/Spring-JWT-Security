package com.demo.jwt.project.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.jwt.project.dto.PermissionDto;
import com.demo.jwt.project.dto.RoleDto;
import com.demo.jwt.project.dto.UserDto;
import com.demo.jwt.project.repository.UserRepository;
import com.demo.jwt.project.user.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String email;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		email = jwtService.extractUsername(jwt);
		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			 User user = userRepository.findByEmail(email).get();

			// Map User to UserDTO
			UserDto userDTO = mapUserToUserDato(user);
			
			if (jwtService.isTokenValid(jwt, userDTO)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDTO,
						null, userDTO.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

	// Verify if it is whitelisted path and if yes dont do anything
	@Override
	protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
		return request.getServletPath().contains("/crackit/v1/auth");
	}
	
	private UserDto mapUserToUserDato(User user) {
		// Map User to UserDTO
		UserDto userDTO = new UserDto();
	    userDTO.setEmail(user.getEmail());
	    userDTO.setPassword(user.getPassword());
	    // Convert roles to RoleDTOs
	    List<RoleDto> roleDTOs = user.getRole().stream().map(role -> {
	        RoleDto roleDTO = new RoleDto();
	        roleDTO.setId(role.getId());
	        roleDTO.setRoleType(role.getRoleType());
	        roleDTO.setPermissions(role.getPermissions().stream().map(permission -> {
	            PermissionDto permissionDTO = new PermissionDto();
	            permissionDTO.setId(permission.getId());
	            permissionDTO.setAuthorityType(permission.getAuthorityType());
	            return permissionDTO;
	        }).collect(Collectors.toList()));
	        return roleDTO;
	    }).collect(Collectors.toList());

	    userDTO.setRole(roleDTOs);
	    return userDTO;
	}
}
