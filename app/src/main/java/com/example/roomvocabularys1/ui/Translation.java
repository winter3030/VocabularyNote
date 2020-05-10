package com.example.roomvocabularys1.ui;

public class Translation {
    private String chinese;
    private boolean ischeck;
    private String  viewtype;

    public Translation(String chinese, boolean ischeck, String viewtype) {
        this.chinese = chinese;
        this.ischeck = ischeck;
        this.viewtype=viewtype;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getViewtype() {
        return viewtype;
    }

    public void setViewtype(String viewtype) {
        this.viewtype = viewtype;
    }
}
