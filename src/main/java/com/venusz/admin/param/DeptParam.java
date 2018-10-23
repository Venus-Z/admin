package com.venusz.admin.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DeptParam {
    private Integer id;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15,min = 2,message ="部门名称长度为2-15位")
    private String name;

    private Integer parentId;

    @NotNull(message = "展示顺序不能为空")
    private Integer seq;

    @Length(max = 150,message = "备注内容控制在150以内")
    private String remark;
}
