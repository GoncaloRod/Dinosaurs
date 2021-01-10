package com.goncalorod.dinosaurs

import android.location.Location
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

enum class WeatherCondition {
    THUNDERSTORM, DRIZZLE, RAIN, SNOW, ATMOSPHERE, CLEAR, CLOUDS, NONE
}

class Weather {

    var conditionID : Int = 0

    val condition : WeatherCondition
        get() {
            return when {
                conditionID > 800 -> WeatherCondition.CLOUDS
                conditionID == 800 -> WeatherCondition.CLEAR
                conditionID >= 700 -> WeatherCondition.ATMOSPHERE
                conditionID >= 600 -> WeatherCondition.SNOW
                conditionID >= 500 -> WeatherCondition.RAIN
                conditionID >= 300 -> WeatherCondition.DRIZZLE
                conditionID >= 200 -> WeatherCondition.THUNDERSTORM
                else -> WeatherCondition.NONE
            }
        }

    constructor() { }

    constructor(conditionID: Int) {
        this.conditionID = conditionID
    }

    companion object {

        private const val API_KEY = "f7a247e72a3c9c49e05efa647c53729f"

        suspend fun GetWeatherFromGPS(location : Location?) : Weather = suspendCoroutine {

            if (location == null) {
                it.resume(Weather())
            } else {
                try {
                    var result = ""

                    var urlStr = "https://api.openweathermap.org/data/2.5/weather?lat=${location.latitude}&lon=${location.longitude}&appid=${API_KEY}"

                    val urlc: HttpURLConnection = URL(urlStr).openConnection() as HttpURLConnection
                    urlc.setRequestProperty("User-Agent", "Test")
                    urlc.setRequestProperty("Connection", "close")
                    urlc.setConnectTimeout(1500)
                    urlc.connect()

                    val stream  = urlc.inputStream
                    val isReader = InputStreamReader(stream)
                    val brin = BufferedReader(isReader)

                    var keepReading = true
                    while (keepReading) {
                        var line = brin.readLine()
                        if (line==null){
                            keepReading = false
                        }else{
                            result += line
                        }
                    }
                    brin.close()

                    val jsonObj = JSONObject(result)

                    it.resume(fromJSON(jsonObj))
                } catch (e: Exception) {
                    it.resume(Weather())
                }
            }
        }

        fun fromJSON(jsonObj: JSONObject) : Weather {
            val weatherObj = jsonObj.getJSONArray("weather")[0] as JSONObject
            val weatherID = weatherObj.getInt("id")

            return Weather(weatherID)
        }
    }
}