package com.hackaton.hackatonv100.model.enums;

public enum States {
    CREATED(0),
    ACCEPTED(1),
    CANCELED(2);
    public final int code;
    States(int code) {
        this.code = code;
    }
}