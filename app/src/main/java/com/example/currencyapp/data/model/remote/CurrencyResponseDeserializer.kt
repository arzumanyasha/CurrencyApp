package com.example.currencyapp.data.model.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CurrencyResponseDeserializer : JsonDeserializer<CurrencyResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CurrencyResponse {
        val jsonObject = json?.asJsonObject
        val date = jsonObject?.get("date")?.asString!!
        val baseKey = jsonObject.keySet()?.last()
        val ratesJsonObject = jsonObject.get(baseKey)?.asJsonObject
        val ratesMap = HashMap<String, Double>()
        ratesMap["usd"] = ratesJsonObject?.get("usd")?.asDouble!!
        ratesMap["byn"] = ratesJsonObject.get("byn")?.asDouble!!
        ratesMap["bnb"] = ratesJsonObject.get("bnb")?.asDouble!!
        ratesMap["btc"] = ratesJsonObject.get("btc")?.asDouble!!
        ratesMap["eth"] = ratesJsonObject.get("eth")?.asDouble!!
        ratesMap["eur"] = ratesJsonObject.get("eur")?.asDouble!!
        ratesMap["rub"] = ratesJsonObject.get("rub")?.asDouble!!
        ratesMap["ada"] = ratesJsonObject.get("ada")?.asDouble!!
        ratesMap["sol"] = ratesJsonObject.get("sol")?.asDouble!!
        val rates = Rates(ratesMap)
        val ratesData = hashMapOf(baseKey!! to rates)
        return CurrencyResponse(date, ratesData)
    }

}