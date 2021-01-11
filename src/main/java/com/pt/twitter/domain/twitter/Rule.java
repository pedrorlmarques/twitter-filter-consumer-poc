package com.pt.twitter.domain.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rule {

    private String id;
    private String value;
    private String tag;

}
