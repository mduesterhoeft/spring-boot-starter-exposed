package com.md.exposed

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "spring.exposed")
data class ExposedProperties(var generateDdl: Boolean)