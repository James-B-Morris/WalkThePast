package com.example.walkthepast

class PointsOfInterestModel {
    var modelName:String? = null
    private var modelImageUrl: String? = null

    fun getName(): String {
        return modelName.toString()
    }

    fun setName(name:String) {
        this.modelName = name
    }

    fun getImageUrl(): String? {
        return modelImageUrl
    }

    fun setImageUrl(image_url: String) {
        this.modelImageUrl = image_url
    }
}