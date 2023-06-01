package cn.nf.chatgptserver.controller;

import cn.nf.chatgptserver.config.Const;
import cn.nf.chatgptserver.entity.DBUser;
import cn.nf.chatgptserver.entity.JwtUser;
import cn.nf.chatgptserver.service.ILoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cn.nf.chatgptserver.entity.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class LoginController {

    private ILoginService loginService;
    @Autowired
    public RedisTemplate redisTemplate;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Result login(@RequestBody DBUser info, HttpServletResponse response) {
        log.info("login {}",info);
        Result result = loginService.login(info);
        if (result.isSuccess())
            response.setHeader(Const.AUTH_HEADER, String.valueOf(result.getData()));
        return result;
    }

    @GetMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }

    @GetMapping("/curUser")
    public Result getCurUser(HttpServletRequest request) {
        String key = String.valueOf(request.getAttribute(Const.USER_CACHE_KEY));
        JwtUser loginUser = (JwtUser) redisTemplate.opsForValue().get(key);
        DBUser user = loginUser.getInfo();
        user.setPassword(null);
        return Result.success(user);
    }

}
