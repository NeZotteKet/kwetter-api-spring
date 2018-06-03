package nl.rensph.kwetter.kweet

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface KweetRepository : JpaRepository<Kweet, Long> {

    /**
     * Get Timeline
     */
    @Query(
            value = "SELECT t.* FROM kweets t " +
                    "JOIN following f ON (t.user_id IN (SELECT following_id FROM following WHERE user_id = ?1)) " +
                    "UNION " +
                    "SELECT * FROM kweets WHERE user_id = ?1 " +
                    "ORDER BY `date` DESC",
            nativeQuery = true)
    fun getTimeline(userId: Long): List<Kweet>


}