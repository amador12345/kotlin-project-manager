package com.for_the_love_of_code.kotlin_pm.config

import com.for_the_love_of_code.kotlin_pm.aop.logging.LoggingAspect

import io.github.jhipster.config.JHipsterConstants

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment

@Configuration
@EnableAspectJAutoProxy
class LoggingAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    fun loggingAspect(env: Environment): LoggingAspect {
        return LoggingAspect(env)
    }
}