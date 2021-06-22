package com.miwfem.socialtourismnetwork.presentation.mapper

import com.miwfem.socialtourismnetwork.businesslogic.model.*
import com.miwfem.socialtourismnetwork.presentation.common.model.MessageVO
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.common.model.TiaLocationVO
import com.miwfem.socialtourismnetwork.presentation.common.model.UserVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.CategoryVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO

fun UserVO.map(): UserEntity = UserEntity(email, name)

fun List<LocationEntity>.map(): List<LocationVO> = map { it.map() }

fun LocationEntity.map(): LocationVO = LocationVO(name, areaName)

fun PostVO.map(): PostEntity =
    PostEntity(id, user, userName, location, area, category, comment, isFav, withFav)

@JvmName("mapPostEntity")
fun List<PostEntity>.map(): List<PostVO> = map { it.map() }

fun PostEntity.map(): PostVO =
    PostVO(id, user, userName, location, area, category, comment, isFav, withFav)

@JvmName("mapCategoryEntity")
fun List<CategoryEntity>.map(): List<CategoryVO> = map { it.map() }

fun CategoryVO.map(): CategoryEntity = CategoryEntity(name)

fun CategoryEntity.map(): CategoryVO = CategoryVO(name)

fun MessageVO.map(): MessageEntity =
    MessageEntity(userEmissary, userEmissaryEmail, postId, userReceptor, message, post)

fun MessageEntity.map(): MessageVO =
    MessageVO(userEmissary, userEmissaryEmail, postId, userReceptor, message, post)

@JvmName("mapMessageEntity")
fun List<MessageEntity>.map(): List<MessageVO> = map { it.map() }

@JvmName("mapTiaLocationEntity")
fun List<TiaLocationEntity>.map(): List<TiaLocationVO> = map { it.map() }

fun TiaLocationEntity.map(): TiaLocationVO = TiaLocationVO(
    activeVerified,
    totalVerified,
    verified,
    date,
    location,
    activeAccumulatedRate,
    accumulatedRateTotal,
    accumulatedRate
)