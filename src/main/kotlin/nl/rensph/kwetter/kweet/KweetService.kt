package nl.rensph.kwetter.kweet

import nl.rensph.kwetter.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Service
class KweetService {

    @Autowired
    lateinit var kweetRepository: KweetRepository

    @Autowired
    lateinit var userRepository: UserRepository

    /**
     * Get all Kweets
     */
    fun getAllKweets(): List<Kweet> =
            kweetRepository.findAll()

    /**
     * Create a new Kweet
     */
    fun createKweet(@Valid @RequestBody kweet: Kweet): ResponseEntity<Kweet> {

        val newKweet = kweetRepository.save(kweet)
        return ResponseEntity.ok(newKweet)

    }

    /**
     * Get a single Kweet
     */
    fun getKweetById(@PathVariable(value = "id") kweetId: Long): ResponseEntity<Kweet> {

        return kweetRepository.findById(kweetId).map { kweet ->
            ResponseEntity.ok(kweet)
        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Update a Kweet
     */
    fun updateKweetById(@PathVariable(value = "id") kweetId: Long,
                        @Valid @RequestBody newKweet: Kweet): ResponseEntity<Kweet> {

        return kweetRepository.findById(kweetId).map { existingKweet ->

            val updatedKweet: Kweet = existingKweet
                    .copy(text = existingKweet.text)

            ResponseEntity.ok().body(kweetRepository.save(updatedKweet))

        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Delete a Kweet
     */
    fun deleteKweetById(kweetId: Long): ResponseEntity<Void> {

        return kweetRepository.findById(kweetId).map { kweet ->

            kweetRepository.delete(kweet)

            ResponseEntity<Void>(HttpStatus.OK)

        }.orElse(ResponseEntity.notFound().build())

    }

    /**
     * Get timeline for user
     */
    fun getTimeline(userId: Long): ResponseEntity<List<Kweet>> =
            ResponseEntity.ok().body(kweetRepository.getTimeline(userId))

    /**
     * Get own kweets for user
     */
    fun getUserKweets(userId: Long): ResponseEntity<List<Kweet>> {

        return userRepository.findById(userId).map { user ->
            ResponseEntity.ok(user.kweets)
        }.orElse(ResponseEntity.notFound().build())

    }

}