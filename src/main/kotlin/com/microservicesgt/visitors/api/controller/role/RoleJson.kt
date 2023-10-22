package com.microservicesgt.visitors.api.controller.role

class RoleJson {
    var id: Long? = null
    var name: String? = null
    var code: String? = null
    var active: Boolean? = null
    var menuList: List<RoleMenuJsonDto>? = null

    companion object {
        fun build(init: RoleJson.() -> Unit) = RoleJson().apply(init)
    }
}