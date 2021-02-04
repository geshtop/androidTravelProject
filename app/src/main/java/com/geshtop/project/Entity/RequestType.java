package com.geshtop.project.Entity;

import androidx.room.TypeConverter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public enum RequestType {
    Created(0), Accepted(1),  Run(2) , Done(3), Paid(4);
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

    public static List<String> getTypseAsList(){
      return   Stream.of(RequestType.values())
                .map(RequestType::name)
                .collect(Collectors.toList());
    }
}
