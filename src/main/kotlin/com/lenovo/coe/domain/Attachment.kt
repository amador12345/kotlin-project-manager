package com.lenovo.coe.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DBRef
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

import java.io.Serializable

/**
 * A Attachment.
 */
@Document(collection = "attachment")
class Attachment(

    @Id
    var id: String? = null,

    @get: NotNull
    @Field("filename")
    var filename: String? = null,

    @DBRef
    @Field("projects")
    var projects: MutableSet<Project> = mutableSetOf(),

    @DBRef
    @Field("tasks")
    var tasks: MutableSet<Task> = mutableSetOf(),

    @DBRef
    @Field("milestones")
    var milestones: MutableSet<Milestone> = mutableSetOf()

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
) : Serializable {

    fun addProject(project: Project): Attachment {
        this.projects.add(project)
        return this
    }

    fun removeProject(project: Project): Attachment {
        this.projects.remove(project)
        return this
    }

    fun addTask(task: Task): Attachment {
        this.tasks.add(task)
        return this
    }

    fun removeTask(task: Task): Attachment {
        this.tasks.remove(task)
        return this
    }

    fun addMilestone(milestone: Milestone): Attachment {
        this.milestones.add(milestone)
        return this
    }

    fun removeMilestone(milestone: Milestone): Attachment {
        this.milestones.remove(milestone)
        return this
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Attachment) return false
        if (other.id == null || id == null) return false

        return id == other.id
    }

    override fun hashCode() = 31

    override fun toString() = "Attachment{" +
        "id=$id" +
        ", filename='$filename'" +
        "}"


    companion object {
        private const val serialVersionUID = 1L
    }
}
