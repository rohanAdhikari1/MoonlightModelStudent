package com.rohan.moonlightmodelstudent.api

import com.google.gson.annotations.Expose

data class ResponseModel(
    @Expose val message: String,
    val status: Int,
    @Expose val result: Result
)