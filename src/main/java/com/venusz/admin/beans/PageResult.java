package com.venusz.admin.beans;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class PageResult<T> {

    private List<T> data = Lists.newArrayList();

    private int total = 0;
}
