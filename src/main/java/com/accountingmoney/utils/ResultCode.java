package com.accountingmoney.utils;

public enum ResultCode {
    SUCCESS("0"),
    ACCOUNT_NOT_FOUND("100"),
    NO_MONEY("101"),
    ERROR("102");

    private final String code;

    ResultCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
