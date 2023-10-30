package com.cstore.domain.report;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Period {
    TODAY("TODAY"),
    THIS_WEEK("THIS_WEEK"),
    THIS_MONTH("THIS_MONTH"),
    THIS_YEAR("THIS_YEAR"),
    YESTERDAY("YESTERDAY"),
    LAST_WEEK("LAST_WEEK"),
    LAST_MONTH("LAST_MONTH"),
    LAST_YEAR("LAST_YEAR");

    private final String period;
}
