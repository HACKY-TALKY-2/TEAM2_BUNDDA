package com.hack.bundda.global;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Result {
    private String message;
    private Boolean ok;
}
