package com.venusz.admin.param;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TestVo {
    @NotBlank
    private String msg;

    @NotNull
    private Integer id;
}
