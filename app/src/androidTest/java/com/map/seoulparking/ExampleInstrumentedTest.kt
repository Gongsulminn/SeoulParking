package com.map.seoulparking

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.map.seoulparking.sqlite.AppDataBase
import com.map.seoulparking.sqlite.FavoritePark
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        var favoirteModel: FavoritePark = AppDataBase.getInstance(InstrumentationRegistry.getTargetContext())?.parkDao()?.isFavorite("173864")!!
        assertEquals("173864", favoirteModel.parkingCode)
    }
}
