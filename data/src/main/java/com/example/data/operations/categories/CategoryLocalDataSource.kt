package com.example.data.operations.categories

class CategoryLocalDataSource(private val categoryDao: CategoryDao) {
    fun saveCategories(categories: List<CategoryEntity>) {
        categoryDao.insertAll(categories)
    }

    fun getCategories(): List<CategoryEntity> {
        return categoryDao.getAll()
    }
}