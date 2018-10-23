package com.venusz.admin.dto;

import com.google.common.collect.Lists;
import com.venusz.admin.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {
    private List<DeptLevelDto> deptLevelDtos = Lists.newArrayList();

    public static DeptLevelDto addDept(SysDept sysDept){
        DeptLevelDto deptLevelDto = new DeptLevelDto();
        BeanUtils.copyProperties(sysDept,deptLevelDto);
        return deptLevelDto;
    }
}
