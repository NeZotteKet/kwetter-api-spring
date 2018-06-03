package nl.rensph.kwetter.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
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
@RequestMapping("/api/users")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Secured("ROLE_USER", "ROLE_ADMIN")
    @GetMapping("")
    fun getAllUsers(): List<User> =
            userService.getAllUsers()

    @PostMapping("")
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<User> =
            userService.createUser(user)

    @GetMapping("/{id}")
    fun getUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<User> =
            userService.getUserById(userId)

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable(value = "id") userId: Long,
                       @Valid @RequestBody newUser: User): ResponseEntity<User> =
            userService.updateUserById(userId, newUser)

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable(value = "id") userId: Long): ResponseEntity<Void> =
            userService.deleteUserById(userId)

    @GetMapping("/{id}/following")
    fun getFollowingById(@PathVariable(value = "id") userId: Long): ResponseEntity<List<User>> =
            userService.getFollowingById(userId)

    @PostMapping("/{id}/following/{followingId}")
    fun addFollowingById(@PathVariable(value = "id") userId: Long,
                         @PathVariable(value = "followingId") followingId: Long): ResponseEntity<List<User>> =
            userService.addFollowingById(userId, followingId)

    @GetMapping("/{id}/followers")
    fun getFollowersById(@PathVariable(value = "id") userId: Long): ResponseEntity<List<User>> =
            userService.getFollowersById(userId)

}