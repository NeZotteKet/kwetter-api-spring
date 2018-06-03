package nl.rensph.kwetter.user

import com.fasterxml.jackson.annotation.JsonIgnore
import nl.rensph.kwetter.kweet.Kweet
import nl.rensph.kwetter.role.Role
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @NotBlank
        @Column(unique = true)
        var username: String = "",

        @NotBlank
        @Column(unique = true)
        var email: String = "",

        @NotBlank
        @JsonIgnore
        var password: String = "",

        var profilePicture: String = "",

        var location: String = "",

        var website: String = "",

        var bio: String = "",

        @Column(nullable = false, updatable = false)
        @Temporal(TemporalType.TIMESTAMP)
        @CreatedDate
        val createdAt: Date? = null,

        @Column(nullable = false)
        @Temporal(TemporalType.TIMESTAMP)
        @LastModifiedDate
        val updatedAt: Date? = null
) : Serializable {

    /**
     * All roles
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = [(JoinColumn(name = "user_id"))],
               inverseJoinColumns = [(JoinColumn(name = "role_id"))])
    var roles: Set<Role> = emptySet()

    /**
     * Relation to Kweets
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var kweets: Set<Kweet> = emptySet()


    /**
     * Relation to following
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "following",
            joinColumns = [(JoinColumn(name = "user_id", referencedColumnName = "id", unique = false))],
            inverseJoinColumns = [(JoinColumn(name = "following_id", referencedColumnName = "id", unique = false))]
    )
    var following: Set<User> = emptySet()

    /**
     * Relation to followers
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "following",
            joinColumns = [(JoinColumn(name = "following_id", referencedColumnName = "id", unique = false))],
            inverseJoinColumns = [(JoinColumn(name = "user_id", referencedColumnName = "id", unique = false))]
    )
    var followers: Set<User> = emptySet()

    /**
     * Follow a user
     */
    fun follow(user: User) {
        this.following = this.following.plus(user)
    }
}


