package com.example.arvind.studentinfo;

/**
 * Created by Arvind on 31-05-2018.
 */

class Blog {
    String ndate,ndepartment,ndescription,nrole,ntitle;

    public Blog() {
    }

    public String getNdate() {
        return ndate;
    }

    public void setNdate(String ndate) {
        this.ndate = ndate;
    }

    public String getNdepartment() {
        return ndepartment;
    }

    public void setNdepartment(String ndepartment) {
        this.ndepartment = ndepartment;
    }

    public String getNdescription() {
        return ndescription;
    }

    public void setNdescription(String ndescription) {
        this.ndescription = ndescription;
    }

    public String getNrole() {
        return nrole;
    }

    public void setNrole(String nrole) {
        this.nrole = nrole;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public Blog(String ndate, String ndepartment, String ndescription, String nrole, String ntitle) {

        this.ndate = ndate;
        this.ndepartment = ndepartment;
        this.ndescription = ndescription;
        this.nrole = nrole;
        this.ntitle = ntitle;
    }
}
