package com.etc.jobsystem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.etc.jobsystem.entity.Company;
import com.etc.jobsystem.mapper.CompanyMapper;
import com.etc.jobsystem.service.CompanyService;

import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
