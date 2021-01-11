package com.pt.twitter.utils;

import com.pt.twitter.domain.CreateRuleRequest;
import com.pt.twitter.domain.DeleteRuleRequest;
import com.pt.twitter.domain.InternalRule;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class RulesUtils {

    private RulesUtils() {
    }

    public static Function<DeleteRuleRequest, List<String>> convertDeleteRuleRequestToMap() {
        return internalRule -> internalRule
                .getRules()
                .stream()
                .map(InternalRule::getId)
                .collect(Collectors.toList());
    }

    public static Function<CreateRuleRequest, Map<String, String>> convertCreateRuleRequestToMap() {
        return internalRule -> internalRule.getRules()
                .stream()
                .collect(Collectors.toMap(InternalRule::getValue, InternalRule::getTag));
    }

    public static String getFormattedString(String string, Map<String, String> rules) {
        StringBuilder sb = new StringBuilder();
        if (rules.size() == 1) {
            String key = rules.keySet().iterator().next();
            return String.format(string, "{\"value\": \"" + key + "\", \"tag\": \"" + rules.get(key) + "\"}");
        } else {
            for (Map.Entry<String, String> entry : rules.entrySet()) {
                String value = entry.getKey();
                String tag = entry.getValue();
                sb.append("{\"value\": \"").append(value).append("\", \"tag\": \"").append(tag).append("\"}").append(",");
            }
            String result = sb.toString();
            return String.format(string, result.substring(0, result.length() - 1));
        }
    }

    public static String getFormattedString(String string, List<String> ids) {
        StringBuilder sb = new StringBuilder();
        if (ids.size() == 1) {
            return String.format(string, "\"" + ids.get(0) + "\"");
        } else {
            for (String id : ids) {
                sb.append("\"" + id + "\"" + ",");
            }
            String result = sb.toString();
            return String.format(string, result.substring(0, result.length() - 1));
        }
    }
}
