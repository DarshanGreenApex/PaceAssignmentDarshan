package com.example.taskassignmentpacedarshan.di.api

class ApiException(val reason: Reason): Exception() {

    enum class Reason {
        UNAUTHORIZED,
        SERVER_ERROR,
        NETWORK_ERROR
    }
}