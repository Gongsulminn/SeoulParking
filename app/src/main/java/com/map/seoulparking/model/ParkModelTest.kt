package com.map.seoulparking.model

/**
 * Created by user on 2018-12-18.
 */
data class ParkModelTest(val parkingCode: String
                         , val parkingName: String
                         , val addr: String
                         , val tel: String = "no data"
                         , val weekdayBeginTime: String
                         , val weekdayEndTime: String
                         , val weekendBeginTime: String
                         , val weekendEndTime: String
                         , val holidayBeginTime: String
                         , val holidayEndTime: String
                         , val payNM: String
                         , val saturdayPayNM: String
                         , val holidayPayNM: String
                         , val fullTimeMonthly: String
                         , val rates: String
                         , val timeRate: String
                         , val addRates: String
                         , val addTimeRate : String
                         , val lat: String
                         , val lng: String) {

}
