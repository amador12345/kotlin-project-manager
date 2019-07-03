package com.for_the_love_of_code.kotlin_pm.web.rest

import com.for_the_love_of_code.kotlin_pm.domain.Project
import com.for_the_love_of_code.kotlin_pm.repository.ProjectRepository
import com.for_the_love_of_code.kotlin_pm.repository.search.ProjectSearchRepository
import com.for_the_love_of_code.kotlin_pm.web.rest.errors.BadRequestAlertException

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


import org.elasticsearch.index.query.QueryBuilders.queryStringQuery

/**
 * REST controller for managing [com.for_the_love_of_code.kotlin_pm.domain.Project].
 */
@RestController
@RequestMapping("/api")
class ProjectResource(
    val projectRepository: ProjectRepository,
    val projectSearchRepository: ProjectSearchRepository
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /projects` : Create a new project.
     *
     * @param project the project to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new project, or with status `400 (Bad Request)` if the project has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projects")
    fun createProject(@Valid @RequestBody project: Project): ResponseEntity<Project> {
        log.debug("REST request to save Project : {}", project)
        if (project.id != null) {
            throw BadRequestAlertException("A new project cannot already have an ID", ENTITY_NAME, "idexists")
        }
        val result = projectRepository.save(project)
        projectSearchRepository.save(result)
        return ResponseEntity.created(URI("/api/projects/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /projects` : Updates an existing project.
     *
     * @param project the project to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated project,
     * or with status `400 (Bad Request)` if the project is not valid,
     * or with status `500 (Internal Server Error)` if the project couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projects")
    fun updateProject(@Valid @RequestBody project: Project): ResponseEntity<Project> {
        log.debug("REST request to update Project : {}", project)
        if (project.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = projectRepository.save(project)
        projectSearchRepository.save(result)
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, project.id.toString()))
            .body(result)
    }

    /**
     * `GET  /projects` : get all the projects.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the [ResponseEntity] with status `200 (OK)` and the list of projects in body.
     */
    @GetMapping("/projects")    
    fun getAllProjects(@RequestParam(required = false, defaultValue = "false") eagerload: Boolean): MutableList<Project> {
        log.debug("REST request to get all Projects")
        return projectRepository.findAllWithEagerRelationships()
    }

    /**
     * `GET  /projects/:id` : get the "id" project.
     *
     * @param id the id of the project to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the project, or with status `404 (Not Found)`.
     */
    @GetMapping("/projects/{id}")
    fun getProject(@PathVariable id: Long): ResponseEntity<Project> {
        log.debug("REST request to get Project : {}", id)
        val project = projectRepository.findOneWithEagerRelationships(id)
        return ResponseUtil.wrapOrNotFound(project)
    }

    /**
     * `DELETE  /projects/:id` : delete the "id" project.
     *
     * @param id the id of the project to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/projects/{id}")
    fun deleteProject(@PathVariable id: Long): ResponseEntity<Void> {
        log.debug("REST request to delete Project : {}", id)

        projectRepository.deleteById(id)
        projectSearchRepository.deleteById(id)
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build()
    }

    /**
     * `SEARCH  /_search/projects?query=:query` : search for the project corresponding
     * to the query.
     *
     * @param query the query of the project search.
     * @return the result of the search.
     */
    @GetMapping("/_search/projects")
    fun searchProjects(@RequestParam query: String): MutableList<Project> {
        log.debug("REST request to search Projects for query {}", query)
        return projectSearchRepository.search(queryStringQuery(query))
            .toMutableList()
    }


    companion object {
        private const val ENTITY_NAME = "project"
    }
}
