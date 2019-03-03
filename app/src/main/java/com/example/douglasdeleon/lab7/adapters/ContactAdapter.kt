package com.example.douglasdeleon.lab7.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import com.example.douglasdeleon.lab7.R
import com.example.douglasdeleon.lab7.data.Contact
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter : ListAdapter<Contact, ContactAdapter.ContactHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.name == newItem.name && oldItem.email == newItem.email
                        && oldItem.number == newItem.number
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val currentContact: Contact = getItem(position)

        holder.textViewName.text = currentContact.name
        holder.textViewNumber.text = currentContact.number.toString()
        holder.textViewEmail.text = currentContact.email
    }

    fun getContactAt(position: Int): Contact {
        return getItem(position)
    }

    inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewName: TextView = itemView.text_view_name
        var textViewNumber: TextView = itemView.text_view_number
        var textViewEmail: TextView = itemView.text_view_email
    }

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}