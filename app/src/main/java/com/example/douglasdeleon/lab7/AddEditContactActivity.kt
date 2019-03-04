package com.example.douglasdeleon.lab7

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_edit_contact.*


class AddEditContactActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.douglasdeleon.lab7.EXTRA_ID"
        const val EXTRA_NAME = "com.example.douglasdeleon.lab7.EXTRA_NAME"
        const val EXTRA_EMAIL = "com.example.douglasdeleon.lab7.EXTRA_EMAIL"
        const val EXTRA_NUMBER = "com.example.douglasdeleon.lab7.EXTRA_NUMBER"
        const val EXTRA_PRIORITY = "com.example.douglasdeleon.lab7.EXTRA_PRIORITY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_contact)

//        number_picker_priority.minValue = 1
//        number_picker_priority.maxValue = 10

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Contact"
            edit_text_name.setText(intent.getStringExtra(EXTRA_NAME))
            edit_text_email.setText(intent.getStringExtra(EXTRA_EMAIL))
            number_picker_number.value = intent.getIntExtra(EXTRA_PRIORITY, 1)
        } else {
            title = "Add Contact"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_contact_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_contact -> {
                saveContact()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveContact() {
        if (edit_text_name.text.toString().trim().isBlank() || edit_text_email.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty contact!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NAME, edit_text_name.text.toString())
            putExtra(EXTRA_EMAIL, edit_text_email.text.toString())
            putExtra(EXTRA_PRIORITY, number_picker_number.value)
            putExtra(EXTRA_NUMBER, edit_text_number.text.toString())

            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
