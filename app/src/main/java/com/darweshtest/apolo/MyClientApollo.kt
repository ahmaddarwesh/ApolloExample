package com.darweshtest.apolo

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


class MyClientApollo {
    companion object{
        var myUrl = "https://heros-game.herokuapp.com/graphql"
        var myApolloClient: ApolloClient? = null

        fun getMyClient(): ApolloClient {

            val http = HttpLoggingInterceptor()
            http.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(http)
                .build()

            myApolloClient = ApolloClient.builder()
                .serverUrl(myUrl)
                .okHttpClient(okHttpClient)
                .build()

            return myApolloClient!!
        }
    }

}