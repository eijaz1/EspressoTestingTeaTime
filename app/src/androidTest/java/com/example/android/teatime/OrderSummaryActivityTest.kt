/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.teatime

import android.app.Instrumentation.ActivityResult
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.matcher.IntentMatchers.isInternal
import android.support.test.espresso.matcher.ViewMatchers.withId

import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot.not

import android.app.Activity
import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderSummaryActivityTest {

    /**
     *
     * This test demonstrates Espresso Intents using the IntentsTestRule, a class that extends
     * ActivityTestRule. IntentsTestRule initializes Espresso-Intents before each test that is annotated
     * with @Test and releases it once the test is complete. The designated Activity
     * is also terminated after each test.
     *
     */

    @Rule
    var mActivityRule = IntentsTestRule<OrderSummaryActivity>(
            OrderSummaryActivity::class.java)


    @Before
    fun stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    fun clickSendEmailButton_SendsEmail() {

        onView(withId(R.id.send_email_button)).perform(click())
        // intended(Matcher<Intent> matcher) asserts the given matcher matches one and only one
        // intent sent by the application.
        intended(allOf(
                hasAction(Intent.ACTION_SENDTO),
                hasExtra(Intent.EXTRA_TEXT, emailMessage)))

    }

    companion object {

        private val emailMessage = "I just ordered a delicious tea from TeaTime. Next time you are craving a tea, check them out!"
    }
}