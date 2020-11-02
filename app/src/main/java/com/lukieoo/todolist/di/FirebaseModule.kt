package com.lukieoo.todolist.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton


@Module
object FirebaseModule  {

        @JvmStatic
        @Singleton
        @Provides
        fun provideFirebaseFirestore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }

        @JvmStatic
        @Provides
        fun provideColFirestore(db: FirebaseFirestore): CollectionReference {
            return db.collection("todoList")
        }


}