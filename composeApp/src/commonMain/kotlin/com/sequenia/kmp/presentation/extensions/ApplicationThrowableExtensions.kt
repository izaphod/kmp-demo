package com.sequenia.kmp.presentation.extensions

import com.sequenia.kmp.data.network.ApplicationThrowable
import com.sequenia.kmp.data.network.NetworkThrowable
import org.jetbrains.compose.resources.StringResource
import sequeniakmp.composeapp.generated.resources.Res
import sequeniakmp.composeapp.generated.resources.error_network_io
import sequeniakmp.composeapp.generated.resources.error_network_parsing
import sequeniakmp.composeapp.generated.resources.error_network_server
import sequeniakmp.composeapp.generated.resources.error_network_timeout
import sequeniakmp.composeapp.generated.resources.error_network_unknown

fun ApplicationThrowable.defineStringResource(): StringResource {
    return when (this) {
        is NetworkThrowable -> {
            when (this) {
                is NetworkThrowable.IO -> Res.string.error_network_io
                is NetworkThrowable.Parsing -> Res.string.error_network_parsing
                is NetworkThrowable.Timeout -> Res.string.error_network_timeout
                is NetworkThrowable.ServerError -> Res.string.error_network_server
                is NetworkThrowable.Unknown -> Res.string.error_network_unknown
            }
        }
        else -> Res.string.error_network_unknown
    }
}