package com.example.nearfruit.data.response

import com.example.nearfruit.data.model.ModelResults
import com.google.gson.annotations.SerializedName

class ModelResultNearby {

    @SerializedName("results")
    val modelResults: List<ModelResults> = emptyList()
}