package com.example.domain.di

import com.example.domain.operations.books.GetBooks
import com.example.domain.operations.categories.GetCategories
import com.example.domain.operations.chars.GetChars
import com.example.domain.operations.houses.GetHouses
import org.koin.dsl.module

class DomainKoinConfiguration {
    fun getModule() = module {
        factory { GetCategories(get()) }
        factory { GetBooks(get()) }
        factory { GetHouses(get()) }
        factory { GetChars(get()) }
    }
}