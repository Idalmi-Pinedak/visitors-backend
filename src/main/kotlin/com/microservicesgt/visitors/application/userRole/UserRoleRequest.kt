package com.microservicesgt.visitors.application.userRole

class UserRoleRequest {
    var userId: Long = 0L
    var roleId: Long = 0L

    companion object {
        fun build(init: UserRoleRequest.() -> Unit) = UserRoleRequest().apply(init)
    }
}
