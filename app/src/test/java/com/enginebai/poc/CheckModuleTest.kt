package com.enginebai.poc

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.enginebai.core.card.Poker
import com.enginebai.poc.data.DomainRepository
import com.enginebai.poc.data.user.UserDataHelper
import com.enginebai.poc.di.koin.appAggregatorModules
import com.enginebai.poc.di.koin.domainAggregatorModules
import com.enginebai.poc.ui.domain.DomainFragmentViewModel
import com.example.feature.data.CardRepository
import com.example.feature.ui.CardViewModel
import io.mockk.every
import io.mockk.mockk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class CheckModuleTest : KoinTest {

    private val mockContext: Context = mockk()

    @BeforeTest
    fun setup() {
        startKoin {
            androidContext(mockContext)
            modules(module {
                includes(appAggregatorModules())
                includes(domainAggregatorModules)
            })
        }
    }

    @Test
    fun `test injections`() {
        assertNotNull(get<CardRepository>())
        assertNotNull(get<UserDataHelper>())
        assertNotNull(get<ComplexInjection>())
        assertNotNull(get<CardViewModel>())
        assertNotNull(get<DomainRepository>())
        assertNotNull(get<Poker>())
        assertNotNull(get<DomainFragmentViewModel>())
        assertNotNull(get<MainViewModel>())
        assertNotNull(get<String>())
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}