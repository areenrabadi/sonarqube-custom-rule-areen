package com.dgcash.emi.attachment.util;

import com.dgcash.emi.attachment.data.dto.request.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringUtil {

    private StringUtil() {
    }

    public static String replacePlaceHolders(List<Field> fields, String directoryPattern) {
        Map<String, String> fieldsMap = fields.stream().collect(Collectors.toMap(Field::getName, Field::getValue));
        for (Map.Entry<String, String> arg : fieldsMap.entrySet()) {
            directoryPattern = StringUtils
                    .replace(directoryPattern, getPlaceHolderForArgument(arg.getKey()), arg.getValue());
        }
        return directoryPattern;
    }

    private static String getPlaceHolderForArgument(String key) {
        return "{?" + key + "}";
    }
}
