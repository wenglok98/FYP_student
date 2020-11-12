package com.example.fyp_student.Class;

public class Subject {
    String SubjectCode, SubjectName;

    public Subject(String subjectCode, String subjectName) {
        SubjectCode = subjectCode;
        SubjectName = subjectName;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
}
