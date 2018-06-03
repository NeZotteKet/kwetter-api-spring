package nl.rensph.kwetter.kweet

import org.springframework.beans.factory.annotation.Autowired
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
@RequestMapping("/api/kweets")
class KweetController {

    @Autowired
    lateinit var kweetService: KweetService

    @GetMapping("")
    fun getAllKweets(): List<Kweet> =
            kweetService.getAllKweets()

    @PostMapping("")
    fun createKweet(@Valid @RequestBody kweet: Kweet): ResponseEntity<Kweet> =
            kweetService.createKweet(kweet)

    @GetMapping("/{id}")
    fun getKweetById(@PathVariable(value = "id") kweetId: Long): ResponseEntity<Kweet> =
            kweetService.getKweetById(kweetId)

    @PutMapping("/{id}")
    fun updateKweetById(@PathVariable(value = "id") kweetId: Long,
                        @Valid @RequestBody newKweet: Kweet): ResponseEntity<Kweet> =
            kweetService.updateKweetById(kweetId, newKweet)

    @DeleteMapping("/{id}")
    fun deleteKweetById(@PathVariable(value = "id") kweetId: Long): ResponseEntity<Void> =
            kweetService.deleteKweetById(kweetId)

    @GetMapping("/{id}/timeline")
    fun getTimelineByUserId(@PathVariable(value = "id") userId: Long): ResponseEntity<List<Kweet>> =
            kweetService.getTimeline(userId)

    @GetMapping("/{id}/kweets")
    fun getUserKweetsByUserId(@PathVariable(value = "id") userId: Long): ResponseEntity<List<Kweet>> =
            kweetService.getUserKweets(userId)

}