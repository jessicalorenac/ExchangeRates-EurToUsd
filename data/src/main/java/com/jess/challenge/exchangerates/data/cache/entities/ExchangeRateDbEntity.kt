package com.jess.challenge.exchangerates.data.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


//Different symbols are not supported in this example, to support them we should
//include a foreignkey to a Symbol table, which is not going to be added for this
//challenge
@Entity(tableName = "exchangeRate")
data class ExchangeRateDbEntity(@PrimaryKey (autoGenerate = true) var id: Long?,
                                @ColumnInfo(name = "date") var rateDate: LocalDate,
                                @ColumnInfo(name = "val_rate") var rateVal: Float)