package com.example.demo.domain.exceptions

class DomainException : RuntimeException {
    lateinit var errorCode: String;
    var errorMessages: List<String>? = null;

    constructor(){}

    constructor(errorCode: String) {
        this.errorCode = errorCode;
    }

    constructor(errorCode: String, errorMessages:String) {
        this.errorCode = errorCode;
        this.errorMessages = arrayListOf(errorMessages);
    }

    constructor(errorCode: String, errorMessages: List<String>) {
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
    }
}