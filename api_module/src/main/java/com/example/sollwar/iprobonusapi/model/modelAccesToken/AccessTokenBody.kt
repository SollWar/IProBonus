package com.example.sollwar.iprobonusapi.model.modelAccesToken

data class AccessTokenBody(
    var accessToken: String = "",
    var idClient: String = "",
    var latitude: Int = 0,
    var longitude: Int = 0,
    var paramName: String = "device",
    var paramValue: String = "",
    var sourceQuery: Int = 0
)