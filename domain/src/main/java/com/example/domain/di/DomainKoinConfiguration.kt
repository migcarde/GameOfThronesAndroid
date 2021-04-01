package com.example.domain.di

import com.example.domain.operations.categories.GetCategories
import org.koin.dsl.module

class DomainKoinConfiguration {
    fun getModule() = module {
        factory { GetCategories(get()) }
    }
}