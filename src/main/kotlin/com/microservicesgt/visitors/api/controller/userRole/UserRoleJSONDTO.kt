package com.microservicesgt.visitors.api.controller.userRole

class UserRoleJSONDTO {
    var id: Long? = 0L
    var userId: Long? = 0L
    var userName: String? = ""
    var roleId: Long? = 0L
    var roleName: String? = ""

    companion object {
        fun build(init: UserRoleJSONDTO.() -> Unit) = UserRoleJSONDTO().apply(init)
    }
}
