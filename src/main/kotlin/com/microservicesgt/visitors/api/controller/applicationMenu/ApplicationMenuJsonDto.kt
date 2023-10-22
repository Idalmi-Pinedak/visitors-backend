package com.microservicesgt.visitors.api.controller.applicationMenu

class ApplicationMenuJsonDto {
    var id: Long? = null
    var name: String? = null
    var icon: String? = null
    var path: String? = null
    var parentId: Long? = null
    var permissions: List<MenuPermissionJsonDto>? = null

    companion object {
        fun build(init: ApplicationMenuJsonDto.() -> Unit) = ApplicationMenuJsonDto().apply(init)
    }

}
