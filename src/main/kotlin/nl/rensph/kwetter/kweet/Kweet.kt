package nl.rensph.kwetter.kweet

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import nl.rensph.kwetter.user.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType


@Entity
@Table(name = "kweets")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(value = ["createdAt", "updatedAt"],
                      allowGetters = true)
data class Kweet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User? = null,

        var text: String = "",

        @Temporal(TemporalType.TIMESTAMP)
        val date: Date? = null,

        @Column(nullable = false, updatable = false)
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        val createdAt: Date? = null,

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        val updatedAt: Date? = null
) : Serializable