package com.bmacharia.springauthservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRoleRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    private Set<String> role;
}
