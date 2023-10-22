package com.microservicesgt.visitors.api.controller.applicationMenu

class MenuPermissionJsonDto {
    var description: String? = ""
    var code: String? = ""

    companion object {
        fun build(init: MenuPermissionJsonDto.() -> Unit) = MenuPermissionJsonDto().apply(init)
    }
}
