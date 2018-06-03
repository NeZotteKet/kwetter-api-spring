package nl.rensph.kwetter.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

    // Get all Users
    @GetMapping("")
    fun getAllUsers(): List<User> =
            userRepository.findAll()

    // Create a new User
    @PostMapping("")
    fun createUser(@Valid @RequestBody user: User): User =
            userRepository.save(user)

    // Get a single User
    @GetMapping("/{id}")
    fun getUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<User> {
        return userRepository.findById(userId).map { user ->

            ResponseEntity.ok(user)

        }.orElse(ResponseEntity.notFound().build())
    }

    // Update a User
    @PutMapping("/{id}")
    fun updateUserById(@PathVariable(value = "id") userId: Long,
                       @Valid @RequestBody newUser: User): ResponseEntity<User> {

        return userRepository.findById(userId).map { existingUser ->

            // Make copy of existing user and
            val updatedUser: User = existingUser
                    .copy(bio = newUser.bio,
                          location = newUser.location)

            ResponseEntity.ok().body(userRepository.save(updatedUser))

        }.orElse(ResponseEntity.notFound().build())

    }

    // Delete a User
    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<Void> {

        return userRepository.findById(userId).map { user ->

            userRepository.delete(user)

            ResponseEntity<Void>(HttpStatus.OK)

        }.orElse(ResponseEntity.notFound().build())

    }

}