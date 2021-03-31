package com.example.commons.data.types

import org.junit.Assert
import org.junit.Test

class EitherTest {

    @Test
    fun `is Left - True`() {
        Assert.assertTrue(Either.Left(Unit).isLeft)
    }

    @Test
    fun `is Left - False`() {
        Assert.assertFalse(Either.Right(Unit).isLeft)
    }

    @Test
    fun  `is Right - True`() {
        Assert.assertTrue(Either.Right(Unit).isRight)
    }

    fun `is Right - False`() {
        Assert.assertFalse(Either.Left(Unit).isRight)
    }

    @Test
    fun `Fold - Left`() {
        val left = Either.Left(Unit)

        left.fold({}, { throw Exception() })
    }

    @Test
    fun `Fold - Right`() {
        val right = Either.Right(Unit)

        right.fold({throw Exception() }, {})
    }

    @Test
    fun `Left value or null - Return left`() {
        val value = "test"
        val left = Either.Left(value)

        Assert.assertEquals(value, left.toLeftValueOrNull())
    }

    @Test
    fun `Left value or null - Return null`() {
        val value = "test"
        val right = Either.Right(value)

        Assert.assertNull(right.toLeftValueOrNull())
    }

    @Test
    fun `Right value or null - Return right`() {
        val value = "test"
        val right = Either.Right(value)

        Assert.assertEquals(value, right.toRightValueOrNull())
    }

    @Test
    fun `Right value or null - Return null`() {
        val value = "test"
        val left = Either.Left(value)

        Assert.assertNull(left.toRightValueOrNull())
    }
}