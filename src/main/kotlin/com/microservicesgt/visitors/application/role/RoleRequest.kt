package com.microservicesgt.visitors.application.role

class RoleRequest {
    var id: Long = 0L
    var name: String = ""
    var code: String = ""
    var active = false
    var menuList = listOf<RoleMenuRequest>()

    companion object {
        fun build(init: RoleRequest.() -> Unit) = RoleRequest().apply(init)
    }
}
