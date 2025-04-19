package com.demo.jwt.project.auth.dto;

import java.util.List;

import com.demo.jwt.project.auth.validator.ValidRoleId;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesRequest {
	
	@NotNull(message = "RoleId must not be null")
    @ValidRoleId
    private Integer id;

    @NotEmpty(message = "RoleType must not be empty")
    @Schema(example = "string")
    @Pattern(
        regexp = "^(ADMIN|MEMBER)$",
        message = "RoleType must be either ADMIN or MEMBER"
    )
    private String roleType;
}
