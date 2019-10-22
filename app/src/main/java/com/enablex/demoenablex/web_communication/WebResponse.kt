package com.enablex.demoenablex.web_communication

interface WebResponse {
    fun onWebResponse(response: String, callCode: Int)
    fun onWebResponseError(error: String, callCode: Int)
}
