package nl.rensph.kwetter.role

import org.hibernate.annotations.NaturalId
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "roles")
data class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Enumerated(EnumType.STRING)
        @NaturalId
        @Column(length = 60)
        var name: RoleName? = null
)