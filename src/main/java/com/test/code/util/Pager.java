package com.test.code.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pager {

    private int current;

    private int size;

    private long totalElements;

    private int totalPages;
}
