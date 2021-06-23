package com.miwfem.socialtourismnetwork.businesslogic.usecase

import android.annotation.SuppressLint
import com.miwfem.socialtourismnetwork.businesslogic.model.TiaLocationEntity
import com.miwfem.socialtourismnetwork.businesslogic.repository.ICMRepository
import com.miwfem.socialtourismnetwork.presentation.common.parseLongDate
import com.miwfem.socialtourismnetwork.presentation.common.parseShortDate
import com.miwfem.socialtourismnetwork.utils.Result
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class GetTiaLocationsNearestDateUseCase(private val cmRepository: ICMRepository) {

    @SuppressLint("SimpleDateFormat")
    suspend fun execute(): Result<List<TiaLocationEntity>> {
        cmRepository.getTia().apply {
            val result = mutableListOf<TiaLocationEntity>()
            data?.let { tiaLocations ->
                val allDates = tiaLocations.map { it.date.parseShortDate() }.distinct()
                val now = Date().toString().parseLongDate()
                var date: String? = null
                now?.let {
                    date = getNearestDate(allDates, it)
                }
                date?.let {
                    tiaLocations.forEach {
                        if (it.date.contains(date.toString())) result.add(it)
                    }
                }
            }
            /*cmRepository.getLocations().apply {
                result.forEach { tiaLocation ->
                    val location = this.data?.find { it.name == tiaLocation.location }
                    location?.areaName
                }
            }*/
            return Result.success(result)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getNearestDate(dates: List<Date?>, targetDate: Date): String? {
        var nearestDate: Date? = null
        var prevDiff: Long = -1
        val formatTia = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val targetTS = targetDate.time
        for (i in dates.indices) {
            val date = dates[i]
            val currDiff = date?.time?.minus(targetTS)?.let { abs(it) }
            if (currDiff != null) {
                if (prevDiff == -1L || currDiff < prevDiff) {
                    prevDiff = currDiff
                    nearestDate = date
                }
            }
        }
        return formatTia.format(nearestDate ?: Date())
    }

}