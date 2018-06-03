package nl.rensph.kwetter.authentication

import javax.validation.constraints.NotBlank

class LoginRequest {
    @NotBlank
    var usernameOrEmail: String? = null

    @NotBlank
    var password: String? = null
}