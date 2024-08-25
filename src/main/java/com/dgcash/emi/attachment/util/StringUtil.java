package com.dgcash.emi.attachment.util;

import com.dgcash.emi.attachment.busniess.exceptions.NotProvidedFieldException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class StringUtil {

    private StringUtil() {
    }

    public static String replacePlaceHolders(Map<String, String> fields, String directoryPattern) {
        return extractFields(directoryPattern)
                .stream()
                .peek(f -> validateFieldIfExist(fields, f.substring(2, f.length() - 1)))
                .reduce(directoryPattern, operator(fields));
    }

    private static void validateFieldIfExist(Map<String, String> fields, String field) {
        if (!fields.containsKey(field)) {
            throw new NotProvidedFieldException();
        }
    }

    private static BinaryOperator<String> operator(Map<String, String> fields) {
        return (pattern, extractedField) -> StringUtils
                .replace(pattern, extractedField, fields.get(extractedField.substring(2, extractedField.length() - 1)));
    }

    private static List<String> extractFields(String directoryPattern) {
        return Pattern.compile("\\{\\?([^}]+)}")
                .matcher(directoryPattern)
                .results()
                .map(MatchResult::group)
                .toList();
    }
}
