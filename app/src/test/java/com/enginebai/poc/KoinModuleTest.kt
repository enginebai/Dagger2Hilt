@file:OptIn(KoinExperimentalAPI::class)

package com.enginebai.poc

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enginebai.poc.di.koin.appAggregatorModules
import io.mockk.mockkClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.verify.verify

class KoinModuleTest : KoinTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create {
        mockkClass(it)
    }

    @Test
    fun `verify all koin modules`() {

    }
}