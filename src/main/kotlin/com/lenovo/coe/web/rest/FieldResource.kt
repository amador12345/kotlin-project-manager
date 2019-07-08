package com.lenovo.coe.web.rest

import com.lenovo.coe.domain.Field
import com.lenovo.coe.service.FieldService
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
 * REST controller for managing [com.lenovo.coe.domain.Field].
 */
@RestController
@RequestMapping("/api")
class FieldResource(
    private val fieldService: FieldService
) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Value("\${jhipster.clientApp.name}")
    private var applicationName: String? = null

    /**
     * `POST  /fields` : Create a new field.
     *
     * @param field the field to create.
     * @return the [ResponseEntity] with status `201 (Created)` and with body the new field, or with status `400 (Bad Request)` if the field has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fields")
    fun createField(@Valid @RequestBody field: Field): ResponseEntity<Field> {
        log.debug("REST request to save Field : {}", field)
        if (field.id != null) {
            throw BadRequestAlertException("A new field cannot already have an ID", ENTITY_NAME, "idexists")
        }
        val result = fieldService.save(field)
        return ResponseEntity.created(URI("/api/fields/" + result.id))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.id.toString()))
            .body(result)
    }

    /**
     * `PUT  /fields` : Updates an existing field.
     *
     * @param field the field to update.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the updated field,
     * or with status `400 (Bad Request)` if the field is not valid,
     * or with status `500 (Internal Server Error)` if the field couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fields")
    fun updateField(@Valid @RequestBody field: Field): ResponseEntity<Field> {
        log.debug("REST request to update Field : {}", field)
        if (field.id == null) {
            throw BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull")
        }
        val result = fieldService.save(field)
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, field.id.toString()))
            .body(result)
    }

    /**
     * `GET  /fields` : get all the fields.
     *
     * @return the [ResponseEntity] with status `200 (OK)` and the list of fields in body.
     */
    @GetMapping("/fields")    
    fun getAllFields(): MutableList<Field> {
        log.debug("REST request to get all Fields")
        return fieldService.findAll()
    }

    /**
     * `GET  /fields/:id` : get the "id" field.
     *
     * @param id the id of the field to retrieve.
     * @return the [ResponseEntity] with status `200 (OK)` and with body the field, or with status `404 (Not Found)`.
     */
    @GetMapping("/fields/{id}")
    fun getField(@PathVariable id: String): ResponseEntity<Field> {
        log.debug("REST request to get Field : {}", id)
        val field = fieldService.findOne(id)
        return ResponseUtil.wrapOrNotFound(field)
    }

    /**
     * `DELETE  /fields/:id` : delete the "id" field.
     *
     * @param id the id of the field to delete.
     * @return the [ResponseEntity] with status `204 (NO_CONTENT)`.
     */
    @DeleteMapping("/fields/{id}")
    fun deleteField(@PathVariable id: String): ResponseEntity<Void> {
        log.debug("REST request to delete Field : {}", id)
        fieldService.delete(id)
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build()
    }

    companion object {
        private const val ENTITY_NAME = "field"
    }
}
