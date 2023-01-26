package com.etc.jobsystem.controller.font;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.etc.jobsystem.VO.UserAllVO;
import com.etc.jobsystem.entity.JsonResult;
import com.etc.jobsystem.entity.ResponseResult;
import com.etc.jobsystem.entity.User;
import com.etc.jobsystem.entity.UserInfo;
import com.etc.jobsystem.exception.MyException;
import com.etc.jobsystem.exception.enums.StateEnums;
import com.etc.jobsystem.resolve.CurrentUser;
import com.etc.jobsystem.service.UserInfoService;
import com.etc.jobsystem.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("userInfo")
@Transactional(noRollbackFor = Exception.class)
// 这个模块太简单了 随便简单写
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;

    @Resource
    UserService userService;

//----------------Get请求的部分--------------Get请求的部分-------------------Get请求的部分----------------------------

    //根据userId 来查询个人信息
    @GetMapping("{userId}")
    public JsonResult getByUserId(@PathVariable int userId){
        QueryWrapper<UserInfo> q = new QueryWrapper<UserInfo>().eq("user_id",userId);
        if(userInfoService.getOne(q)!=null){
            return new JsonResult(userInfoService.getOne(q));
        }else {
            throw new MyException(StateEnums.FIND_ERROR.getCode(),"奶奶滴，这个USER_id 不存在");
        }
    }
    //根据获取当前用户的info信息
    @GetMapping("getUserInfo")
    public JsonResult getUserInfo(@CurrentUser User user){
        QueryWrapper<UserInfo> q = new QueryWrapper<UserInfo>().eq("user_id",user.getUserId());
        UserInfo one = userInfoService.getOne(q);
        UserAllVO userAllVO  = new UserAllVO(user,one);
        return new JsonResult(userAllVO);

    }


//----------------Post请求的部分--------------Post请求的部分-------------------Post请求的部分----------------------------

    //添加个人信息 - 必须传入 userId
    @PostMapping("createUserInfo/{userId}")
    public JsonResult saveUserInfo(@Valid UserInfo userInfo, BindingResult br,@PathVariable int userId){
        if(br.hasErrors()){
            throw new MyException(StateEnums.ADD_ERROR.getCode(),"前端数据填写不完整");
        }
        userInfo.setUserId(userId);
        if(userInfoService.save(userInfo)){
            return new JsonResult(StateEnums.ADD_SUCCESS.getMessage());
        }else{
            throw new MyException(StateEnums.ADD_ERROR.getCode(),"添加异常");
        }
    }

    //添加个人信息  个人中心
    @PostMapping("changeUserInfo")
    public ResponseResult saveUserInfo(@RequestBody UserInfo userInfo, @CurrentUser User user){

        userInfo.setUserId(user.getUserId());
        boolean b = userInfoService.saveOrUpdate(userInfo);

        return ResponseResult.okResult(b);
    }

    //添加个人信息  个人中心
    @PostMapping("changeUser")
    public ResponseResult saveUser( @RequestBody User user){


        boolean b = userService.updateById(user);

        return ResponseResult.okResult(b);
    }

//----------------Put请求的部分--------------Put请求的部分-------------------Put请求的部分----------------------------

    @PutMapping("")
    public JsonResult updateUserInfo(UserInfo userInfo){
        if(userInfoService.updateById(userInfo)){
            return new JsonResult(StateEnums.SUCCESS.getMessage(),"修改成功");
        }else{
            throw new MyException(StateEnums.EDIT_ERROR.getCode(),"修改异常");
        }

    }

//----------------Delete请求的部分--------------Delete请求的部分-------------------Delete请求的部分----------------------------

                //无需delete
}
