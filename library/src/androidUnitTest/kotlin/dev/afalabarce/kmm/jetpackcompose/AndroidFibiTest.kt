package dev.afalabarce.kmm.jetpackcompose

import kotlin.test.Test
import kotlin.test.assertEquals

class AndroidFibiTest {

    @Test
    fun `test 3rd element`() {
        assertEquals(3, fibi.take(3).last())
    }
}