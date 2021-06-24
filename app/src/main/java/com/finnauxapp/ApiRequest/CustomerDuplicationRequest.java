package com.finnauxapp.ApiRequest;

public class CustomerDuplicationRequest {
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String Type;
    public String Value;
}
