package com.example.android.shoppinglist.helpers

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class UtilsTest{
    @Test
    fun testIsConsecutive(){
        assertEquals(true,Utils.isConsecutive("2345"))
        assertEquals(false,Utils.isConsecutive("1345"))
    }
    @Test
    fun testIsSame(){
        assertEquals(true,Utils.isSame("1111"))
        assertEquals(false,Utils.isSame("1314"))
        assertEquals(false,Utils.isSame("4111"))
    }
}