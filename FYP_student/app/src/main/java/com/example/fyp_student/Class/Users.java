package com.example.fyp_student.Class;


public class Users {
    public String uid, ustudentid, uname, utype, ugender;

   public Users(String uid,String ustudentid, String uname, String utype, String ugender)
   {
       this.uid = uid;
       this.ustudentid = ustudentid;
       this.uname = uname;
       this.utype = utype;
       this.ugender = ugender;
   }

    public Users(String uid, String uname) {
        this.uid = uid;
        this.uname = uname;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUstudentid() {
        return ustudentid;
    }

    public void setUstudentid(String ustudentid) {
        this.ustudentid = ustudentid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getUgender() {
        return ugender;
    }

    public void setUgender(String ugender) {
        this.ugender = ugender;
    }


}
