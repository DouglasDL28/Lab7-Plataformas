package com.example.douglasdeleon.lab7.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface ContactDao {
    @Insert
    fun insert (contact: Contact)

    @Update
    fun update (contact: Contact)

    @Delete
    fun delete (contact: Contact)

    @Query("DELETE FROM contact_table")
    fun deleteAllContacts()

    @Query("SELECT * FROM contact_table ORDER BY name DESC")
    fun getAllContacts(): LiveData<List<Contact>>
}