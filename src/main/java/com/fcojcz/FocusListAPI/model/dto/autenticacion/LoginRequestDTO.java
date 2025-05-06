package com.fcojcz.FocusListAPI.model.dto.autenticacion;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

    private String username;
    @Email(message = "El email no es valido")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @AssertTrue(message = "El username o el email deben ser proporcionados")
    public boolean isUsernameOrEmailProvided() {
        boolean hasUsername = username != null && username.isEmpty();
        boolean hasEmail = email != null && email.isEmpty();
        return hasUsername || hasEmail;
    }
}
