package com.example.sailhub;

public class CompetitorData {


    int rank, points, sailNo, PY, elapsed, laps, corrected;
    String boatClass,helmName,crewName;

    public CompetitorData(int rank, String bClass,int sailNo,String helmName,String crewName,int PY,int elapsed,int laps,int corrected,int points){
        this.rank = rank;
        this.boatClass = bClass;
        this.sailNo = sailNo;
        this.helmName = helmName;
        this.crewName = crewName;
        this.PY = PY;
        this.elapsed = elapsed;
        this.laps = laps;
        this.corrected = corrected;
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

    public Integer getElapsed() {
        return elapsed;
    }
    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    public Integer getLaps() {
        return laps;
    }
    public void setLaps(int laps) {
        this.laps = laps;
    }

    public Integer getCorrected() {
        return corrected;
    }
    public void setCorrected(int corrected) {
        this.corrected = corrected;
    }

    public Integer getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
}
