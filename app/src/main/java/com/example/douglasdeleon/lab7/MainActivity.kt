package com.example.douglasdeleon.lab7

import com.example.douglasdeleon.lab7.R
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.douglasdeleon.lab7.adapters.ContactAdapter
import com.example.douglasdeleon.lab7.data.Contact
import com.example.douglasdeleon.lab7.viewmodels.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_CONTACT_REQUEST = 1
        const val EDIT_CONTACT_REQUEST = 2
    }

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddContact.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditContactActivity::class.java),
                ADD_CONTACT_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter = ContactAdapter()

        recycler_view.adapter = adapter

        contactViewModel = ContactViewModel.of(this).get(ContactViewModel::class.java)

        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Contact Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact) {
                var intent = Intent(baseContext, AddEditContactActivity::class.java)
                intent.putExtra(AddEditContactActivity.EXTRA_ID, contact.id)
                intent.putExtra(AddEditContactActivity.EXTRA_NAME, contact.name)
                intent.putExtra(AddEditContactActivity.EXTRA_EMAIL, contact.email)
                intent.putExtra(AddEditContactActivity.EXTRA_NUMBER, contact.number)
                intent.putExtra(AddEditContactActivity.EXTRA_PRIORITY, contact.priority)

                startActivityForResult(intent, EDIT_CONTACT_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_cotacts -> {
                contactViewModel.deleteAllContacts()
                Toast.makeText(this, "All contacts deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val newContact = Contact(
                data!!.getStringExtra(AddEditContactActivity.EXTRA_NAME),
                data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL),
                data.getIntExtra(AddEditContactActivity.EXTRA_PRIORITY, 1),
                data.getIntExtra(AddEditContactActivity.EXTRA_PRIORITY, 1)
            )
            contactViewModel.insert(newContact)

            Toast.makeText(this, "Contact saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditContactActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this@MainActivity, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateContact = Contact(
                data!!.getStringExtra(AddEditContactActivity.EXTRA_NAME),
                data.getStringExtra(AddEditContactActivity.EXTRA_EMAIL),
                data.getIntExtra(AddEditContactActivity.EXTRA_NUMBER, 1),
                data.getIntExtra(AddEditContactActivity.EXTRA_PRIORITY, 1)
            )
            updateContact.id = data.getIntExtra(AddEditContactActivity.EXTRA_ID, -1)
            contactViewModel.update(updateContact)

        } else {
            Toast.makeText(this, "Contact not saved!", Toast.LENGTH_SHORT).show()
        }


    }

}