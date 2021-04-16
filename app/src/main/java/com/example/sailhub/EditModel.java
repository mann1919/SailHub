package com.example.sailhub;

/*
This class is used to save data even when scrolled in recycler view
adopted from: https://demonuts.com/android-recyclerview-with-edittext/
 */
public class EditModel {
    private String etClass,etPY,etSailNo,etHelmName,etCrewName,tvIndex,tvIndexTwo,tvDisplayClass,tvDisplaySailNo,tvDisplayHelmName,etElapsed,etLaps,tvDisplayPY;


    public String getEtClassValue() {
        return etClass;
    }
    public void setEtClassValue(String etClass) {
        this.etClass = etClass;
    }

    public String getEtPYValue() {
        return etPY;
    }
    public void setEtPYValue(String etPY) {
        this.etPY = etPY;
    }

    public String getEtSailNoValue() {
        return etSailNo;
    }
    public void setEtSailNoValue(String etSailNo) {
        this.etSailNo = etSailNo;
    }

    public String getEtHelmNameValue() {
        return etHelmName;
    }
    public void setEtHelmNameValue(String etHelmName) {
        this.etHelmName = etHelmName;
    }

    public String getEtCrewNameValue() {
        return etCrewName;
    }
    public void setEtCrewNameValue(String etCrewName) {
        this.etCrewName = etCrewName;
    }

    public String getTvIndexValue() { return tvIndex; }
    public void setTvIndexValue(String tvIndex) {
        this.tvIndex = tvIndex;
    }

    public String getTvIndexTwoValue() { return tvIndexTwo; }
    public void setTvIndexTwoValue(String tvIndexTwo) {
        this.tvIndexTwo = tvIndexTwo;
    }


    public String getTvClassValue() { return tvDisplayClass; }
    public void setTvClassValue(String tvDisplayClass) {
        this.tvDisplayClass = tvDisplayClass;
    }

    public String getTvSailNoValue() { return tvDisplaySailNo; }
    public void setTvSailNoValue(String tvDisplaySailNo) {
        this.tvDisplaySailNo = tvDisplaySailNo;
    }

    public String getTvHelmNameValue() { return tvDisplayHelmName; }
    public void setTvHelmNameValue(String tvDisplayHelmName) { this.tvDisplayHelmName = tvDisplayHelmName; }

    public String getTvPYValue() { return tvDisplayPY; }
    public void setTvPYValue(String tvDisplayPY) { this.tvDisplayPY = tvDisplayPY; }

    public String getEtElapsedValue() { return etElapsed; }
    public void setEtElapsedValue(String etElapsed) {
        this.etElapsed = etElapsed;
    }

    public String getEtLapsValue() { return etLaps; }
    public void setEtLapsValue(String etLaps) {
        this.etLaps = etLaps;
    }
}
