package com.map.seoulparking.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018-12-18.
 */

public class ParkModel implements Parcelable {
    @SerializedName("parkingCode")
    @Expose
    private String parkingCode;
    @SerializedName("parkingName")
    @Expose
    private String parkingName;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("tel")
    @Expose
    private String tel;
    @SerializedName("payNM")
    @Expose
    private String payNM;
    @SerializedName("weekdayBeginTime")
    @Expose
    private String weekdayBeginTime;
    @SerializedName("weekdayEndTime")
    @Expose
    private String weekdayEndTime;
    @SerializedName("weekendBeginTime")
    @Expose
    private String weekendBeginTime;
    @SerializedName("weekendEndTime")
    @Expose
    private String weekendEndTime;
    @SerializedName("holidayBeginTime")
    @Expose
    private String holidayBeginTime;
    @SerializedName("holidayEndTime")
    @Expose
    private String holidayEndTime;
    @SerializedName("saturdayPayNM")
    @Expose
    private String saturdayPayNM;
    @SerializedName("holidayPayNM")
    @Expose
    private String holidayPayNM;
    @SerializedName("fullTimeMonthly")
    @Expose
    private String fullTimeMonthly;
    @SerializedName("rates")
    @Expose
    private String rates;
    @SerializedName("timeRate")
    @Expose
    private String timeRate;
    @SerializedName("addRates")
    @Expose
    private String addRates;
    @SerializedName("addTimeRate")
    @Expose
    private String addTimeRate;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;

    public String getParkingCode() {
        return parkingCode;
    }

    public void setParkingCode(String parkingCode) {
        this.parkingCode = parkingCode;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPayNM() {
        return payNM;
    }

    public void setPayNM(String payNM) {
        this.payNM = payNM;
    }

    public String getWeekdayBeginTime() {
        return weekdayBeginTime;
    }

    public void setWeekdayBeginTime(String weekdayBeginTime) {
        this.weekdayBeginTime = weekdayBeginTime;
    }

    public String getWeekdayEndTime() {
        return weekdayEndTime;
    }

    public void setWeekdayEndTime(String weekdayEndTime) {
        this.weekdayEndTime = weekdayEndTime;
    }

    public String getWeekendBeginTime() {
        return weekendBeginTime;
    }

    public void setWeekendBeginTime(String weekendBeginTime) {
        this.weekendBeginTime = weekendBeginTime;
    }

    public String getWeekendEndTime() {
        return weekendEndTime;
    }

    public void setWeekendEndTime(String weekendEndTime) {
        this.weekendEndTime = weekendEndTime;
    }

    public String getHolidayBeginTime() {
        return holidayBeginTime;
    }

    public void setHolidayBeginTime(String holidayBeginTime) {
        this.holidayBeginTime = holidayBeginTime;
    }

    public String getHolidayEndTime() {
        return holidayEndTime;
    }

    public void setHolidayEndTime(String holidayEndTime) {
        this.holidayEndTime = holidayEndTime;
    }

    public String getSaturdayPayNM() {
        return saturdayPayNM;
    }

    public void setSaturdayPayNM(String saturdayPayNM) {
        this.saturdayPayNM = saturdayPayNM;
    }

    public String getHolidayPayNM() {
        return holidayPayNM;
    }

    public void setHolidayPayNM(String holidayPayNM) {
        this.holidayPayNM = holidayPayNM;
    }

    public String getFullTimeMonthly() {
        return fullTimeMonthly;
    }

    public void setFullTimeMonthly(String fullTimeMonthly) {
        this.fullTimeMonthly = fullTimeMonthly;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getTimeRate() {
        return timeRate;
    }

    public void setTimeRate(String timeRate) {
        this.timeRate = timeRate;
    }

    public String getAddRates() {
        return addRates;
    }

    public void setAddRates(String addRates) {
        this.addRates = addRates;
    }

    public String getAddTimeRate() {
        return addTimeRate;
    }

    public void setAddTimeRate(String addTimeRate) {
        this.addTimeRate = addTimeRate;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parkingCode);
        dest.writeString(this.parkingName);
        dest.writeString(this.addr);
        dest.writeString(this.tel);
        dest.writeString(this.payNM);
        dest.writeString(this.weekdayBeginTime);
        dest.writeString(this.weekdayEndTime);
        dest.writeString(this.weekendBeginTime);
        dest.writeString(this.weekendEndTime);
        dest.writeString(this.holidayBeginTime);
        dest.writeString(this.holidayEndTime);
        dest.writeString(this.saturdayPayNM);
        dest.writeString(this.holidayPayNM);
        dest.writeString(this.fullTimeMonthly);
        dest.writeString(this.rates);
        dest.writeString(this.timeRate);
        dest.writeString(this.addRates);
        dest.writeString(this.addTimeRate);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
    }

    public ParkModel() {
    }

    public ParkModel(String parkingCode, String parkingName, String addr, String tel, String payNM, String weekdayBeginTime, String weekdayEndTime, String weekendBeginTime, String weekendEndTime, String holidayBeginTime, String holidayEndTime, String saturdayPayNM, String holidayPayNM, String fullTimeMonthly, String rates, String timeRate, String addRates, String addTimeRate, String lat, String lng) {
        this.parkingCode = parkingCode;
        this.parkingName = parkingName;
        this.addr = addr;
        this.tel = tel;
        this.payNM = payNM;
        this.weekdayBeginTime = weekdayBeginTime;
        this.weekdayEndTime = weekdayEndTime;
        this.weekendBeginTime = weekendBeginTime;
        this.weekendEndTime = weekendEndTime;
        this.holidayBeginTime = holidayBeginTime;
        this.holidayEndTime = holidayEndTime;
        this.saturdayPayNM = saturdayPayNM;
        this.holidayPayNM = holidayPayNM;
        this.fullTimeMonthly = fullTimeMonthly;
        this.rates = rates;
        this.timeRate = timeRate;
        this.addRates = addRates;
        this.addTimeRate = addTimeRate;
        this.lat = lat;
        this.lng = lng;
    }

    protected ParkModel(Parcel in) {
        this.parkingCode = in.readString();
        this.parkingName = in.readString();
        this.addr = in.readString();
        this.tel = in.readString();
        this.payNM = in.readString();
        this.weekdayBeginTime = in.readString();
        this.weekdayEndTime = in.readString();
        this.weekendBeginTime = in.readString();
        this.weekendEndTime = in.readString();
        this.holidayBeginTime = in.readString();
        this.holidayEndTime = in.readString();
        this.saturdayPayNM = in.readString();
        this.holidayPayNM = in.readString();
        this.fullTimeMonthly = in.readString();
        this.rates = in.readString();
        this.timeRate = in.readString();
        this.addRates = in.readString();
        this.addTimeRate = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
    }

    public static final Creator<ParkModel> CREATOR = new Creator<ParkModel>() {
        @Override
        public ParkModel createFromParcel(Parcel source) {
            return new ParkModel(source);
        }

        @Override
        public ParkModel[] newArray(int size) {
            return new ParkModel[size];
        }
    };
}
