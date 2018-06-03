package nl.rensph.kwetter.authentication

import nl.rensph.kwetter.role.Role
import nl.rensph.kwetter.role.RoleName
import nl.rensph.kwetter.role.RoleRepository
import nl.rensph.kwetter.security.JwtTokenProvider
import nl.rensph.kwetter.user.User
import nl.rensph.kwetter.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid


@RestController
@RequestMapping("/api/authentication")
class AuthenticationController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var tokenProvider: JwtTokenProvider

    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<*> {

        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        loginRequest.usernameOrEmail,
                        loginRequest.password
                )
        )

        SecurityContextHolder.getContext().setAuthentication(authentication)

        val jwt = tokenProvider.generateToken(authentication)

        return ResponseEntity.ok(jwt)
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {

        // Creating user's account
        val user = User(username = signUpRequest.username!!,
                        email = signUpRequest.email!!,
                        password = signUpRequest.password!!)

        user.password = passwordEncoder.encode(user.password)

        val userRole: Role = roleRepository.findByName(RoleName.ROLE_USER)

        user.roles = user.roles.plus(userRole)

        val result = userRepository.save(user)

        val location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.username).toUri()

        return ResponseEntity.created(location).body<Any>("Successfully created")
    }
}