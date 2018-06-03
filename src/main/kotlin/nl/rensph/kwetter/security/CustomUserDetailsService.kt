package nl.rensph.kwetter.security

import nl.rensph.kwetter.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    internal var userRepository: UserRepository? = null

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        // Let people login with either username or email
        val user = userRepository!!.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow { UsernameNotFoundException("User not found with username or email : $usernameOrEmail") }

        return UserPrincipal.create(user)
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    open fun loadUserById(id: Long?): UserDetails {
        val user = userRepository!!.findById(id!!).orElseThrow {
            UsernameNotFoundException("User not found with id : $id")
        }

        return UserPrincipal.create(user)
    }
}
