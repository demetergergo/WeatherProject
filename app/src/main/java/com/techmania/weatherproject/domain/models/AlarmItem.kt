package com.techmania.weatherproject.domain.models

import java.time.LocalDateTime

data class AlarmItem(
    val time: LocalDateTime,
    val message: String,
    val title: String,
)