package com.microservicesgt.visitors.api.controller.shared

class PageJsonDto<T>(val content: List<T>, val totalElements: Long = 0)
