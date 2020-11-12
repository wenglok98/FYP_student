package com.example.fyp_student.Class;

public class Enrollment {
    String EnrollID,SubjectCode, SubjectName, usid , visitedminutes , resultgrade;

    public Enrollment(String enrollID, String subjectCode, String subjectName, String usid, String visitedminutes, String resultgrade) {
        EnrollID = enrollID;
        SubjectCode = subjectCode;
        SubjectName = subjectName;
        this.usid = usid;
        this.visitedminutes = visitedminutes;
        this.resultgrade = resultgrade;
    }

    public String getResultgrade() {
        return resultgrade;
    }

    public void setResultgrade(String resultgrade) {
        this.resultgrade = resultgrade;
    }

    public Enrollment(String visitedminutes) {
        this.visitedminutes = visitedminutes;
    }

    public String getVisitedminutes() {
        return visitedminutes;
    }

    public Enrollment(String enrollID, String subjectCode, String subjectName, String usid, String visitedminutes) {
        EnrollID = enrollID;
        SubjectCode = subjectCode;
        SubjectName = subjectName;
        this.usid = usid;
        this.visitedminutes = visitedminutes;
    }

    public void setVisitedminutes(String visitedminutes) {
        this.visitedminutes = visitedminutes;
    }

    public Enrollment(String SubjectCode, String SubjectName, String usid) {
        this.SubjectCode = SubjectCode;
        this.SubjectName = SubjectName;
        this.usid = usid;
    }

    public Enrollment(String SubjectCode, String SubjectName) {
        this.SubjectCode = SubjectCode;
        this.SubjectName = SubjectName;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public Enrollment() {
    }

    public Enrollment(String EnrollID, String SubjectCode, String SubjectName, String usid) {
        this.EnrollID = EnrollID;
        this.SubjectCode = SubjectCode;
        this.SubjectName = SubjectName;
        this.usid = usid;
    }

    public String getEnrollID() {
        return EnrollID;
    }

    public void setEnrollID(String EnrollID) {
        this.EnrollID = EnrollID;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String getSubjectCode) {
        this.SubjectCode = getSubjectCode;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String setSubjectName) {
        this.SubjectName = setSubjectName;
    }


}
