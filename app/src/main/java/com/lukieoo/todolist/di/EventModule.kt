package com.lukieoo.todolist.di

import com.lukieoo.todolist.events.FeedbackEvent
import com.lukieoo.todolist.events.NavEvent
import dagger.Module
import dagger.Provides
import io.reactivex.processors.PublishProcessor
import javax.inject.Singleton

@Module
class EventModule {

    @Provides
    @Singleton
    fun providesNavEvents(): PublishProcessor<NavEvent> = PublishProcessor.create()

    @Provides
    @Singleton
    fun providesFeedbackEvent(): PublishProcessor<FeedbackEvent> = PublishProcessor.create()

}