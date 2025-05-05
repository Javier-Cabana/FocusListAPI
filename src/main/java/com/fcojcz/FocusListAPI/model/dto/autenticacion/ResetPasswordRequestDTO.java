package com.fcojcz.FocusListAPI.model.dto.autenticacion;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequestDTO {
    @NotBlank
    private String token;
    @NotBlank
    private String newPassword;
}
