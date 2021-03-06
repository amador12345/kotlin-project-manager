package com.lenovo.coe.web.rest

import com.lenovo.coe.domain.Task
import com.lenovo.coe.repository.TaskRepository
import com.lenovo.coe.web.rest.errors.BadRequestAlertException

import io.github.jhipster.web.util.HeaderUtil
import io.github.jhipster.web.util.ResponseUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import java.net.URI
import java.net.URISyntaxException

/**
 * REST controller for managing [com.lenovo.coe.domain.Task].
 */
@RestController
@RequestMapping("/api")
class TaskResource(
    private val taskRepository: TaskRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /tasks` : Create a new task.
     *
     * @param task the task to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new task, or with status `400 (Bad Request)` if the task has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tasks")
    fun createTask(@Valid @RequestBody task: Task): ResponseEntity<Task> {
        log.debug("REST request to save Task : {}", task)
        if (task.id != null) {
            throw BadRequestAlertException("A new task cannot already have an ID", ENTITY_NAME, "idexists")
        }
        val result = taskRepository.save(task)
        return ResponseEntity.created(URI("/api/tasks/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /tasks` : Updates an existing task.
     *
     * @param task the task to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated task,
     * or with status `400 (Bad Request)` if the task is not valid,
     * or with status `500 (Internal Server Error)` if the task couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tasks")
    fun updateTask(@Valid @RequestBody task: Task): ResponseEntity<Task> {
        log.debug("REST request to update Task : {}", task)
        if (task.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = taskRepository.save(task)
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, task.id.toString()))
            .body(result)
    }

    /**
     * `GET  /tasks` : get all the tasks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the [ResponseEntity] with status `200 (OK)` and the list of tasks in body.
     */
    @GetMapping("/tasks")    
    fun getAllTasks(@RequestParam(required = false, defaultValue = "false") eagerload: Boolean): MutableList<Task> {
        log.debug("REST request to get all Tasks")
        return taskRepository.findAllWithEagerRelationships()
    }

    /**
     * `GET  /tasks/:id` : get the "id" task.
     *
     * @param id the id of the task to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the task, or with status `404 (Not Found)`.
     */
    @GetMapping("/tasks/{id}")
    fun getTask(@PathVariable id: String): ResponseEntity<Task> {
        log.debug("REST request to get Task : {}", id)
        val task = taskRepository.findOneWithEagerRelationships(id)
        return ResponseUtil.wrapOrNotFound(task)
    }

    /**
     * `DELETE  /tasks/:id` : delete the "id" task.
     *
     * @param id the id of the task to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable id: String): ResponseEntity<Void> {
        log.debug("REST request to delete Task : {}", id)

        taskRepository.deleteById(id)
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build()
    }

    companion object {
        private const val ENTITY_NAME = "task"
    }
}
