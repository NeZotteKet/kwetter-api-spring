package nl.rensph.kwetter.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    /**
     * Get all Users
     */
    fun getAllUsers(): List<User> =
            userRepository.findAll()

    // Create a new User
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<User> {

        user.password = passwordEncoder.encode(user.password)
        val newUser = userRepository.save(user)
        return ResponseEntity.ok(newUser)

    }

    /**
     * Get a single User
     */
    fun getUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<User> {

        return userRepository.findById(userId).map { user ->
            ResponseEntity.ok(user)
        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Update a User
     */
    fun updateUserById(@PathVariable(value = "id") userId: Long,
                       @Valid @RequestBody newUser: User): ResponseEntity<User> {

        return userRepository.findById(userId).map { existingUser ->

            val updatedUser: User = existingUser
                    .copy(bio = newUser.bio,
                          location = newUser.location)

            ResponseEntity.ok().body(userRepository.save(updatedUser))

        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Delete a User
     */
    fun deleteUserById(userId: Long): ResponseEntity<Void> {

        return userRepository.findById(userId).map { user ->

            userRepository.delete(user)

            ResponseEntity<Void>(HttpStatus.OK)

        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Get the following for User
     */
    fun getFollowingById(userId: Long): ResponseEntity<List<User>> {

        return userRepository.findById(userId).map { user ->
            ResponseEntity.ok(user.following)
        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Add the following for User
     */
    fun addFollowingById(userId: Long, followingId: Long): ResponseEntity<List<User>> {

        return userRepository.findById(followingId).map { following ->
            userRepository.findById(userId).map { user ->

                user.follow(following)
                userRepository.save(user)
                ResponseEntity.ok().body(user.following)

            }.orElse(ResponseEntity.notFound().build())
        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Get the followers for User
     */
    fun getFollowersById(userId: Long): ResponseEntity<List<User>> {

        return userRepository.findById(userId).map { user ->
            ResponseEntity.ok(user.followers)
        }.orElse(ResponseEntity.notFound().build())

    }

}