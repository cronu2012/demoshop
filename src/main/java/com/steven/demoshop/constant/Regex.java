package com.steven.demoshop.constant;

public enum Regex {

    PASSWORD("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,12}$"),
    EMAIL("^\\w{1,63}@[a-zA-Z\\d]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$"),
    NAME ("[\\u4e00-\\u9fa5_a-zA-Z\\d]{3,20}"),
    PHONE ("09\\d{2}-?\\d{3}-?\\d{3}"),

    //簡易日期判定
    BIRTHDAY ("(((?:19|20)[0-9]{2})[- /.](0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01]))");

    private String regexString;

    Regex(String regexString) {
        this.regexString =regexString;
    }

    public String getRegexString(){
        return regexString;
    }
}
