package com.example.lottietest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.yandex.div.DivDataTag
import com.yandex.div.coil.CoilDivImageLoader
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div.lottie.DivLottieExtensionHandler
import com.yandex.div2.DivData
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val divConfiguration = DivConfiguration.Builder(CoilDivImageLoader(this))
            .extension(DivLottieExtensionHandler())
            .build()
        val div2Context = Div2Context(
            baseContext = this,
            configuration = divConfiguration,
            lifecycleOwner = this
        )
        val divView = Div2View(div2Context)
        application.resources.openRawResource(R.raw.card).bufferedReader().use {
            val json = JSONObject(it.readText())
            val templates = json.getJSONObject("templates")
            val card = json.getJSONObject("card")
            val env = DivParsingEnvironment(ParsingErrorLogger.LOG)
            env.parseTemplates(templates)
            val divData = DivData.invoke(env, card)
            divView.setData(divData, DivDataTag(divData.logId))
        }
        setContentView(divView)
    }
}
