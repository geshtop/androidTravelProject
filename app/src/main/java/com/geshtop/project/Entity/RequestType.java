package com.geshtop.project.Entity;

import androidx.room.TypeConverter;


public enum RequestType {
    Created(0), Accepted(1), Done(2), Paid(4);
    private final Integer code;
    RequestType(Integer value) {
        this.code = value;
    }
    public Integer getCode() {
        return code;
    }

    @TypeConverter
    public static RequestType getType(Integer numeral) {
        for (RequestType ds : values())
            if (ds.code.equals(numeral))
                return ds;
        return null;
    }
    @TypeConverter
    public static Integer getTypeInt(RequestType requestType) {
        if (requestType != null)
            return requestType.code;
        return null;
    }
}
