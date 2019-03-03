package com.example.douglasdeleon.lab7.data

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask

@Database(entities = [Contact::class], version = 1)
abstract class ContactRoomDatabase: RoomDatabase(){

    abstract fun contactDao(): ContactDao

    companion object {
        private var instance: ContactRoomDatabase? = null

        fun getInstance(context: Context): ContactRoomDatabase? {
            if (instance == null) {
                synchronized(ContactRoomDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactRoomDatabase::class.java, "contact_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ContactRoomDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val contactDao = db?.contactDao()

        override fun doInBackground(vararg p0: Unit?) {
            contactDao?.insert(Contact("name 1", "email 1", 1, null))
            contactDao?.insert(Contact("name 2", "email 2", 2, null))
            contactDao?.insert(Contact("name 3", "email 3", 3, null))
        }
    }

}