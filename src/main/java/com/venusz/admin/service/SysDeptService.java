package com.venusz.admin.service;

import com.alibaba.druid.filter.AutoLoad;
import com.google.common.base.Preconditions;
import com.venusz.admin.dao.SysDeptMapper;
import com.venusz.admin.exception.ParamException;
import com.venusz.admin.model.SysDept;
import com.venusz.admin.param.DeptParam;
import com.venusz.admin.util.BeanValidator;
import com.venusz.admin.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public void save(DeptParam deptParam){
        BeanValidator.check(deptParam);
        if(checkIsExists(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept sysDept = SysDept.builder().name(deptParam.getName()).parentId(deptParam.getParentId())
                .remark(deptParam.getRemark()).seq(deptParam.getSeq()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()),deptParam.getParentId()));
        sysDept.setOperator("system");
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(sysDept);

    }

    /**
     * 校验当前层级是否存在相同的部门
     * @param parentId   父节点ID
     * @param deptName   部门名称
     * @param deptID     当前部门ID
     * @return
     */
    private boolean checkIsExists(Integer parentId,String deptName,Integer deptID){

        return sysDeptMapper.countByNameAndParentId(parentId,deptName,deptID)>0;
    }

    /**
     * 获取当前部门的ID,没有则返回父级ID
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if(dept == null){
            return null;
        }
        return dept.getLevel();
    }

    public void update(DeptParam deptParam) {
        if(checkIsExists(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        Preconditions.checkNotNull(before,"待更新的部门不存在");
        if(checkIsExists(deptParam.getParentId(),deptParam.getName(),deptParam.getId())){
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept after = SysDept.builder().id(deptParam.getId()).name(deptParam.getName()).parentId(deptParam.getParentId())
                .remark(deptParam.getRemark()).seq(deptParam.getSeq()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()),deptParam.getParentId()));
        after.setOperator("system");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());

        //是否更新当前信息以及子节点的信息
        updateWithChild(before,after);
    }

    @Transactional
    void updateWithChild(SysDept before, SysDept after) {

        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if(!after.getLevel().equals(before.getLevel())){
            List<SysDept> childDeptListBylevel = sysDeptMapper.getChildDeptListBylevel(before.getLevel());
            if(CollectionUtils.isEmpty(childDeptListBylevel)){
                for (SysDept sysDept : childDeptListBylevel){
                    String level = sysDept.getLevel();
                    if(level.indexOf(oldLevelPrefix) == 0){
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        sysDept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(childDeptListBylevel);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }
}
