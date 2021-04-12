package com.example.sailhub;

public class FinalCompetitorData {


    int rank, points, sailNo, PY;
    String boatClass,helmName,crewName;

    public FinalCompetitorData(int rank, String bClass,int sailNo,String helmName,String crewName,int PY,int points){
        this.rank = rank;
        this.boatClass = bClass;
        this.sailNo = sailNo;
        this.helmName = helmName;
        this.crewName = crewName;
        this.PY = PY;
        this.points = points;

    }


    public Integer getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setBoatClass(String boatClass) {
        this.boatClass = boatClass;
    }
    public String getBoatClass() {
        return boatClass;
    }

    public Integer getSailNo() {
        return sailNo;
    }
    public void setSailNo(int sailNo) {
        this.sailNo = sailNo;
    }

    public String getHelmName() {
        return helmName;
    }
    public void setHelmName(String helmName) {
        this.helmName = helmName;
    }

    public String getCrewName() {
        return crewName;
    }
    public void setCrewName(String crewName) {
        this.crewName = crewName;
    }

    public Integer getPY() {
        return PY;
    }
    public void setPY(int PY) {
        this.PY = PY;
    }

    public Integer getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
