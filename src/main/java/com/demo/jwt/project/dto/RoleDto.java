package com.demo.jwt.project.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Integer id;
	
	private String roleType;
	
	private List<PermissionDto> permissions;
}
