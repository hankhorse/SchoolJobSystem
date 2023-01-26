package com.etc.jobsystem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.etc.jobsystem.entity.Company;
import com.etc.jobsystem.entity.CompanyHr;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.mapper.CompanyHrMapper;
import com.etc.jobsystem.mapper.CompanyMapper;
import com.etc.jobsystem.mapper.UserMapper;
import com.etc.jobsystem.service.CompanyHrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;


@Service
public class CompanyHrServiceImpl extends ServiceImpl<CompanyHrMapper, CompanyHr> implements CompanyHrService {

    @Resource
    CompanyMapper companyMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public ResponseResult getAllHr(String ownerId) {
        //先找到公司
        int companyId = getCompanyId(ownerId);
        if (companyId==-1) return ResponseResult.okResult("当前用户没有公司");
        //通过公司id查找hr
        LambdaQueryWrapper<CompanyHr> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(CompanyHr::getCompanyId,companyId);
        List<CompanyHr> companyHrs = baseMapper.selectList(lambdaQueryWrapper);
        if (companyHrs.size()<=0) return ResponseResult.okResult("当前公司没有hr");
        //获取hr所有id
        List<Integer> hrlist = companyHrs.stream().map(CompanyHr::getHrId).collect(Collectors.toList());
        //查找hr通过集合
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper();
        userWrapper.in(User::getUserId,hrlist);
        List<User> users1 = userMapper.selectList(userWrapper);
        return ResponseResult.okResult(users1,"查找到以下数据");
    }

    //------------公共方法------------公共方法------------公共方法------------公共方法------------公共方法------------公共方法

    //获取公司id  查找到返回1 没有返回-1
    private int getCompanyId(String ownerId){
        LambdaQueryWrapper<Company> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Company::getCompanyOwnerid,ownerId);
        Company company = companyMapper.selectOne(lambdaQueryWrapper);
        //有可能为空
        if (Objects.isNull(company)) return -1;

        return company.getCompanyId();
    }
}
