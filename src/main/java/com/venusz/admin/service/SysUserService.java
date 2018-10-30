package com.venusz.admin.service;

import com.google.common.base.Preconditions;
import com.venusz.admin.dao.SysUserMapper;
import com.venusz.admin.exception.ParamException;
import com.venusz.admin.model.SysUser;
import com.venusz.admin.param.UserParam;
import com.venusz.admin.util.BeanValidator;
import com.venusz.admin.util.MD5Util;
import com.venusz.admin.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: VenusZ
 * \* Date: 2018/10/30
 * \* Time: 21:47
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public void save(UserParam userParam){
        BeanValidator.check(userParam);
        if(checkEmailExist(userParam.getId(),userParam.getMail())){
            throw new ParamException("邮箱被占用");
        }
        if(checkTelephoneExist(userParam.getId(),userParam.getTelephone())){
            throw new ParamException("电话号码被占用");
        }
        String password = PasswordUtil.randomPassword();
        password = "123456";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser sysUser = SysUser.builder().deptId(userParam.getDeptId()).mail(userParam.getMail())
                .password(encryptedPassword).telephone(userParam.getTelephone()).username(userParam.getUsername())
                .status(userParam.getStatus()).remark(userParam.getRemark()).build();
        sysUser.setOperator("system");
        sysUser.setOperateIp("127.0.0.1");
        sysUser.setOperateTime(new Date());

        //TODO:发送邮件

        sysUserMapper.insertSelective(sysUser);
    }

    public void update(UserParam userParam){
        BeanValidator.check(userParam);
        if(checkEmailExist(userParam.getId(),userParam.getMail())){
            throw new ParamException("邮箱被占用");
        }
        if(checkTelephoneExist(userParam.getId(),userParam.getTelephone())){
            throw new ParamException("电话号码被占用");
        }
        SysUser bofore = sysUserMapper.selectByPrimaryKey(userParam.getId());
        Preconditions.checkNotNull(bofore,"更新的用户不存在");

        SysUser after = SysUser.builder().deptId(userParam.getDeptId()).mail(userParam.getMail())
                .telephone(userParam.getTelephone()).username(userParam.getUsername())
                .status(userParam.getStatus()).remark(userParam.getRemark()).build();
        after.setOperator("system");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkEmailExist(Integer userId,String email){

        return sysUserMapper.countByEmail(email,userId)>0;
    }

    private boolean checkTelephoneExist(Integer userId,String telephone){

        return sysUserMapper.countByTelephone(telephone,userId)>0;
    }

    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }
}
