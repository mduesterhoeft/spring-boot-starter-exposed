package com.github.mduesterhoeft.exposed.noscan

import org.jetbrains.exposed.sql.Table

object OtherThings : Table() {
    var id = long("id").primaryKey().autoIncrement()
    var name = varchar("name", 100)
}