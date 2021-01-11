package com.pt.twitter.utils;

import com.pt.twitter.domain.CreateRuleRequest;
import com.pt.twitter.domain.DeleteRuleRequest;
import com.pt.twitter.domain.InternalRule;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class RulesUtilsTest {

    @Test
    void testConvertCreateRuleRequestToMap() {

        var createRuleRequest = new CreateRuleRequest();
        createRuleRequest.setRules(List.of(new InternalRule("123", "abc", "abc"),
                new InternalRule("12", "bbb", "bbb")));

        var createRules = RulesUtils.convertCreateRuleRequestToMap().apply(createRuleRequest);

        assertThat(createRules)
                .hasSize(2)
                .contains(entry("abc", "abc"))
                .contains(entry("bbb", "bbb"));
    }

    @Test
    void testConvertDeleteRuleRequestToMap() {

        var deleteRuleRequest = new DeleteRuleRequest();
        deleteRuleRequest.setRules(List.of(new InternalRule("123", "abc", "abc"),
                new InternalRule("12", "bbb", "bbb")));

        var idsToDelete = RulesUtils.convertDeleteRuleRequestToMap().apply(deleteRuleRequest);

        assertThat(idsToDelete).hasSize(2).contains("123", "12");
    }

    @Test
    void testAddRulesPatternShouldAddRulesAndTag() {

        var rules = new LinkedHashMap<String, String>();
        rules.put("abc", "abc");
        rules.put("cc", "cc");

        var rulesBody = RulesUtils.getFormattedString("{\"add\": [%s]}", rules);

        assertThat(rulesBody).isNotNull().isEqualTo("{\"add\": [{\"value\": \"abc\", \"tag\": \"abc\"},{\"value\": \"cc\", \"tag\": \"cc\"}]}");
    }

    @Test
    void testAddRulePatternShouldAddRuleAndTag() {

        var rules = new LinkedHashMap<String, String>();
        rules.put("abc", "abc");

        var rulesBody = RulesUtils.getFormattedString("{\"add\": [%s]}", rules);

        assertThat(rulesBody).isNotNull().isEqualTo("{\"add\": [{\"value\": \"abc\", \"tag\": \"abc\"}]}");
    }

    @Test
    void testDeleteRulesPatternShouldAddRulesId() {

        var rules = List.of("abc", "ccc");

        var deleteRulesBody = RulesUtils.getFormattedString("{ \"delete\": { \"ids\": [%s]}}", rules);

        assertThat(deleteRulesBody).isNotNull().isEqualTo("{ \"delete\": { \"ids\": [\"abc\",\"ccc\"]}}");
    }

    @Test
    void testDeleteRulePatternShouldAddRulesId() {

        var rules = List.of("abc");

        var deleteRulesBody = RulesUtils.getFormattedString("{ \"delete\": { \"ids\": [%s]}}", rules);

        assertThat(deleteRulesBody).isNotNull().isEqualTo("{ \"delete\": { \"ids\": [\"abc\"]}}");
    }
}
