package com.lenovo.coe.repository

import com.lenovo.coe.domain.Benefit
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data MongoDB repository for the [Benefit] entity.
 */
@Suppress("unused")
@Repository
interface BenefitRepository : MongoRepository<Benefit, String> {
}