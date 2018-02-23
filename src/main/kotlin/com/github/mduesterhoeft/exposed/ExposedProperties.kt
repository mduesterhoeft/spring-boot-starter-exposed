package com.github.mduesterhoeft.exposed

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "spring.exposed")
data class ExposedProperties(var generateDdl: Boolean)
