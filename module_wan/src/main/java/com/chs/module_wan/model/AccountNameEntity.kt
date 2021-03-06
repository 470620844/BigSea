package com.chs.module_wan.model

/**
 * author：chs
 * date：2020/4/6
 * des：
 */
data class AccountNameEntity(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)