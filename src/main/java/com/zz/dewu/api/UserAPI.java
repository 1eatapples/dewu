package com.zz.dewu.api;

import com.zz.dewu.model.Result;
import com.zz.dewu.model.User;
import com.zz.dewu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author joe
 */
@Controller
public class UserAPI {

    @Autowired
    private UserService userService;
    /**
     * 注册
     *
     */
    @PostMapping("/api/user/reg")
    @ResponseBody
    public Result<User> reg(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd) {
        return userService.register(userName, pwd);
    }
    /**
     * 登录
     *
     */
    @PostMapping("/api/user/login")
    @ResponseBody
    public Result<User> login(@RequestParam("userName") String userName, @RequestParam("pwd") String pwd, HttpServletRequest request) {
        Result<User> result = userService.login(userName, pwd);

        if (result.isSuccess()) {
            request.getSession().setAttribute("userId", result.getData().getId());
        }

        return result;
    }
    /**
     * 登出
     *
     */
    @GetMapping("/api/user/logout")
    @ResponseBody
    public Result logout(HttpServletRequest request) {
        Result result = new Result();
        request.getSession().removeAttribute("userId");

        result.setSuccess(true);
        return result;
    }
     /**
     * 判断是否登录
     *
     */
    @GetMapping("/api/user/checklogin")
    @ResponseBody
    public Result<Boolean> checkLogin(HttpServletRequest request) {
        Result result = new Result();

        result.setSuccess(true);
        result.setData(userService.checkLogin(request));
        return result;
    }

}
