package com.example.douglasdeleon.lab7.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
class Contact (var name: String,
               var email: String,
               var number: Int,
               var priority: Int,
               @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
               var image: ByteArray?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}