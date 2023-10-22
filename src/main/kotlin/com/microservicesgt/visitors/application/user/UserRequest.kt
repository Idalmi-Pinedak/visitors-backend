package com.microservicesgt.visitors.application.user

class UserRequest {
    var userId: Long = 0L
    var active: Boolean = true
    var email: String = ""
    var name: String = ""
    var password: String = ""

    companion object {
        fun build(init: UserRequest.() -> Unit) = UserRequest().apply(init)
    }
}
