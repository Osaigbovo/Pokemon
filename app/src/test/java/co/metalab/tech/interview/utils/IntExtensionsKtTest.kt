package co.metalab.tech.interview.utils

import co.metalab.tech.interview.utils.prettyPrintId
import org.junit.Assert.assertEquals
import org.junit.Test

class IntExtensionsKtTest {

    @Test
    fun prettyPrintId1() {
        val id = 1

        val expected = "#1"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }

    @Test
    fun prettyPrintId10() {
        val id = 10

        val expected = "#10"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }

    @Test
    fun prettyPrintId100() {
        val id = 100

        val expected = "#100"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }

    @Test
    fun prettyPrintIdNull() {
        val id = null

        val expected : String? = "#null"
        val actual = id.prettyPrintId()
        assertEquals(expected, actual)
    }
}