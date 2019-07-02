package com.for_the_love_of_code.kotlin_pm.domain

import com.for_the_love_of_code.kotlin_pm.config.Constants

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.BatchSize
import org.springframework.data.elasticsearch.annotations.FieldType

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import java.io.Serializable
import java.time.Instant
import java.util.Locale
import java.util.Objects

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "user")
class User @JvmOverloads constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    var id: Long? = null,

    login: String? = null,

    @JsonIgnore
    @field:NotNull
    @field:Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    var password: String? = null,

    @field:Size(max = 50)
    @Column(name = "first_name", length = 50)
    var firstName: String? = null,

    @field:Size(max = 50)
    @Column(name = "last_name", length = 50)
    var lastName: String? = null,

    @field:Email
    @field:Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    var email: String? = null,

    @field:NotNull
    @Column(nullable = false)
    var activated: Boolean = false,

    @field:Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    var langKey: String? = null,

    @field:Size(max = 256)
    @Column(name = "image_url", length = 256)
    var imageUrl: String? = null,

    @field:Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    var activationKey: String? = null,

    @field:Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    var resetKey: String? = null,

    @Column(name = "reset_date")
    var resetDate: Instant? = null,

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "name")])

    @BatchSize(size = 20)
    var authorities: MutableSet<Authority> = mutableSetOf(),

    createdBy: String? = null,
    createdDate: Instant? = Instant.now(),
    lastModifiedBy: String? = null,
    lastModifiedDate: Instant? = Instant.now()
) : AbstractAuditingEntity(createdBy, createdDate, lastModifiedBy, lastModifiedDate), Serializable {

    @NotNull
    @field:Pattern(regexp = Constants.LOGIN_REGEX)
    @field:Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    var login: String? = login
    set(value) {
        // Lowercase the login before saving it in database
        field = value?.toLowerCase(Locale.ENGLISH)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (other.id == null || id == null) return false

        return Objects.equals(id, other.id)
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString(): String {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}