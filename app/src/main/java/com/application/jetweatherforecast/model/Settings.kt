package com.application.jetweatherforecast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_tbl")
data class Settings (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "unit")
    val unit: String
)