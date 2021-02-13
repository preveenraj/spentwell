package com.spentwell.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.spentwell.data.models.Expense

@Database(entities = [Expense::class], exportSchema = false, version = 1)
@TypeConverters(
    ExpenseTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao


    companion object {
        private var INSTANCE: AppDatabase? = null
        const val DATABASE_NAME = "spentwell_db"
        fun getInstance(context: Context?): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    if (INSTANCE == null) {
                        context?.let {
                            INSTANCE = Room.databaseBuilder(
                                it.applicationContext,
                                AppDatabase::class.java,
                                DATABASE_NAME
                            )
                                .fallbackToDestructiveMigration()
                                .setJournalMode(JournalMode.TRUNCATE)
                                .enableMultiInstanceInvalidation()
                                .build()
                        }
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}