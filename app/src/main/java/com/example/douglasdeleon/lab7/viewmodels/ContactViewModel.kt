package com.example.douglasdeleon.lab7.viewmodels


import android.arch.lifecycle.AndroidViewModel
import android.app.Application
import android.arch.lifecycle.LiveData
import com.example.douglasdeleon.lab7.data.Contact
import com.example.douglasdeleon.lab7.data.ContactRepository

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: ContactRepository =
        ContactRepository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun update(contact: Contact) {
        repository.update(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }

    fun deleteAllContacts() {
        repository.deleteAllContacts()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}