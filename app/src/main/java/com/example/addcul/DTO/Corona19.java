package com.example.addcul.DTO;

public class Corona19 {
    String date, conf, release, death, exam;

    public Corona19(String date, String conf, String release, String death, String exam) {
        this.date = date;
        this.conf = conf;
        this.release = release;
        this.death = death;
        this.exam = exam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }
}
