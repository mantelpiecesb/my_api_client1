package com.example.lib

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.annotate.JsonProperty
import org.codehaus.jackson.map.ObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class my_APIclient_lib {

    val client = HttpClient.newBuilder().build()
    val objectMapper = ObjectMapper()

    fun getAccessToken(url:String, headers : Array<String>, bodyValues : Map<String,String>): String? {
        val bodyValues = bodyValues

        val requestBody: String = objectMapper
            .writeValueAsString(bodyValues)

        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .headers(*headers)
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //println(response.body())
        val result = objectMapper.readValue(response.body(), ResultPOSTquery::class.java)
        //println(result.accessToken)
        return result.accessToken

    }

    fun getBonusData(url:String, headers : Array<String>): Map<String,String>? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .headers(*headers)
            .build();
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //println(response.body())
        val result = objectMapper.readValue(response.body(), ResultGETquery::class.java)
        return result.data
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ResultGETquery {
    @JsonProperty("data")
    var data: Map<String, String>? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ResultPOSTquery {
    @JsonProperty("accessToken")
    var accessToken: String? = null
}

