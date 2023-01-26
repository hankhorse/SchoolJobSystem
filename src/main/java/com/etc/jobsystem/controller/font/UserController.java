package com.etc.jobsystem.controller.font;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.etc.jobsystem.VO.HrUser;
import com.etc.jobsystem.entity.*;
import com.etc.jobsystem.enums.AppHttpCodeEnum;
import com.etc.jobsystem.enums.RoleEnum;
import com.etc.jobsystem.enums.notice.MsgTypeEnum;
import com.etc.jobsystem.enums.notice.ResultEnum;
import com.etc.jobsystem.resolve.CurrentUser;
import com.etc.jobsystem.service.*;
import com.etc.jobsystem.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisCache redisCache;

    @Resource
    NoticeService noticeService;

    @Resource
    JobService jobService;

    @Resource
    CompanyService companyService;

    @Resource
    CompanyHrService companyHrService;


    //----------------Get请求的部分--------------Get请求的部分-------------------Get请求的部分----------------------------

    //分页查询
    @GetMapping("/admin/{current}/{limit}")
    public JsonResult<User> page(@PathVariable long current, @PathVariable long limit) {
        Page<User> objectPage = new Page<>(current, limit);
        userService.page(objectPage, null);
        long total = objectPage.getTotal();
        List<User> records = objectPage.getRecords();
        return new JsonResult<>(total, records);

    }

    @GetMapping("login")
    public ResponseResult login(String userName, String userPassword) {
        System.out.println("userName" + userName + userPassword);
        return userService.login(userName, userPassword);
    }

    @GetMapping("getUser")
    public ResponseResult getUser(@CurrentUser User user) {
        System.out.println(user);
        //先登录看看能不能存储
        //用错误的token来访问
        //正确的token来访问
        return ResponseResult.okResult(user);
    }

    //获取全部用户
    @GetMapping("getAllUser")
    public JsonResult getAllUser() {
        return new JsonResult(userService.list());
    }


    @GetMapping("testto")
    public ResponseResult testto(@CurrentUser User user) {
        System.out.println(user);
        //先登录看看能不能存储
        //用错误的token来访问
        //正确的token来访问
        return ResponseResult.okResult(user);
    }

    @GetMapping("getUserRoleWithCompany")
    public ResponseResult getUserRoleWithCompany(@CurrentUser User user) {
//       1.获取当前角色如果是通过的管理员返回2
//       2.查看有没有发送消息

//        1
        if (user.getUserRole().equals(RoleEnum.COMPANY_ROLE.getCode()))
            return ResponseResult.okResult(RoleEnum.COMPANY_ROLE.getCode());

//        2  查找 发送者是本用户  审核未通过的数据
//        long count = noticeService.count(new LambdaQueryWrapper<Notice>()
//                .eq(Notice::getNoticeSenderId, user.getUserId())
//                .eq(Notice::getNoticeMsgType, MsgTypeEnum.ADD_COMPANY.getCode())
//                .eq(Notice::getNoticeResult, ResultEnum.NONE_OPTION.getCode()));

//        if (count>0)//存在未审核的记录则标记为不能填写信息
//            return ResponseResult.okResult(RoleEnum.SEND_COMPANY_ROLE.getCode());
//        else  //其他情况就是可以填写
        return ResponseResult.okResult(RoleEnum.NO_SEND_COMPANY_ROLE.getCode());
    }


    //----------------Post请求的部分--------------Post请求的部分-------------------Post请求的部分----------------------------

    //注册  未测试
    @GetMapping("/register")
    //user 是来接收 用户名和密码
    public ResponseResult register(User user) {
        //查一下有没有数据
        System.out.println(user);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getUserName, user.getUserName());
        User one = userService.getOne(lambdaQueryWrapper);
        if (one != null) return ResponseResult.okResult("用户名已存在");
        //可以存入数据库
        user.setUserRole(RoleEnum.NONE_ROLE.getCode());
        user.setUserImg("http://42.194.222.130:8080/usr/download/spring/2.jpg");
        userService.save(user);
        return ResponseResult.okResult(user, "注册成功");

    }
    @PostMapping("/admin/register")
    //user 是来接收 用户名和密码
    public ResponseResult registerAdinn(User user) {
        //查一下有没有数据
        System.out.println(user);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getUserName, user.getUserName());
        User one = userService.getOne(lambdaQueryWrapper);
        if (one != null) return ResponseResult.okResult("用户名已存在");
        //可以存入数据库
        user.setUserRole(RoleEnum.NONE_ROLE.getCode());
        //user.setUserImg("http://42.194.222.130:8080/usr/download/spring/2.jpg");
        userService.save(user);
        return ResponseResult.okResult(user, "注册成功");

    }

    @PostMapping("addHr")
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addHr(@RequestBody HrUser hrUser,@CurrentUser User user) {
        /**
         *    1.找到序列号对应的公司id
         *    2.添加companyhr
         *    3.修改当前用户角色并且返回当前用户
         * */
        //1
        Company company = companyService.getOne(new LambdaQueryWrapper<Company>()
                .eq(Company::getCompanyProof, hrUser.getCompanyProof()));
        //2
        companyHrService.save(new CompanyHr(null,company.getCompanyId(),user.getUserId()));

        //3
        user.setUserEmail(hrUser.getUserEmail());
        user.setUserPhone(hrUser.getUserPhone());
        user.setUserRole(RoleEnum.HR_ROLE.getCode());
        userService.updateById(user);
        return ResponseResult.okResult(user);
    }


    //----------------Put请求的部分--------------Put请求的部分-------------------Put请求的部分----------------------------

    //修改用户信息 未测试
    @PutMapping("/admin") //修改用户角色为普通角色
    public ResponseResult updateUser( User user) {
        user.setUserRole(RoleEnum.USER_ROLE.getCode());
        if (!Objects.isNull(user)) {  //有这个用户
            boolean b = userService.updateById(user);
            if (b) {
                return ResponseResult.okResult(user);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.User_ERRO);
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.User_ERRO);
        }

    }

    //修改用户信息
    @PutMapping("") //修改用户角色为普通角色
    public ResponseResult updateUserBySelect( @CurrentUser User user) {
        user.setUserRole(RoleEnum.USER_ROLE.getCode());
        if (!Objects.isNull(user)) {  //有这个用户
            boolean b = userService.updateById(user);
            if (b) {
                return ResponseResult.okResult(user);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.User_ERRO);
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.User_ERRO);
        }

    }

    //修改用户信息 未测试
    @PutMapping("roleToCompany")
    public ResponseResult roleToCompany(@RequestBody User user) {
        user.setUserRole(RoleEnum.SEND_COMPANY_ROLE.getCode());
        if (!Objects.isNull(user)) {  //有这个用户
            boolean b = userService.updateById(user);
            if (b) {
                return ResponseResult.okResult(user);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.User_ERRO);
            }
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.User_ERRO);
        }

    }

    //  hr发布新的工作
    @PostMapping("publishJob")
    public ResponseResult publishJob(@RequestBody Job job, @CurrentUser User user) {
        job.setUserId(user.getUserId());
        boolean save = jobService.save(job);
        return ResponseResult.okResult("发布成功");
    }


    //----------------Delete请求的部分--------------Delete请求的部分-------------------Delete请求的部分----------------------------

    @DeleteMapping("/admin/deleteUser/{userId}")
    public JsonResult delete(@PathVariable int userId) {
        if (userService.removeById(userId)) {
            return new JsonResult("成功");

        } else {
            return new JsonResult("失败");
        }
    }

}
