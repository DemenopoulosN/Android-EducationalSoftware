package com.unipi.p17019p17024.educationalsoftware;

public class Students {

    private String userID;
    private String email;
    private String name;
    private int problemsScore;
    private int revisionTestScore;
    private int totalAdditionFaults;

    public Students(){

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProblemsScore() {
        return problemsScore;
    }

    public void setProblemsScore(int problemsScore) {
        this.problemsScore = problemsScore;
    }

    public int getRevisionTestScore() {
        return revisionTestScore;
    }

    public void setRevisionTestScore(int revisionTestScore) {
        this.revisionTestScore = revisionTestScore;
    }

    public int getTotalAdditionFaults() {
        return totalAdditionFaults;
    }

    public void setTotalAdditionFaults(int totalAdditionFaults) {
        this.totalAdditionFaults = totalAdditionFaults;
    }
}
