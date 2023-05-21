package cn.nf.chartgptserver.service.impl;

import cn.nf.chartgptserver.config.Const;
import cn.nf.chartgptserver.entity.AuthorizationConfig;
import cn.nf.chartgptserver.entity.JwtUser;
import cn.nf.chartgptserver.entity.DBUser;
import cn.nf.chartgptserver.entity.Result;
import cn.nf.chartgptserver.service.ILoginService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@Slf4j
public class LoginServiceImpl implements ILoginService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    public RedisTemplate redisTemplate;
    @Autowired
    public AuthorizationConfig config;
    @Override
    public Result login(DBUser user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken((StringUtils.isEmpty(user.getEmail())?user.getPhone():user.getEmail()),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(authentication==null){
            return Result.error("Login Failed.");
        }
        JwtUser jwtUser = (JwtUser)authentication.getPrincipal();
        String username = jwtUser.getUsername();
        String token = JWT.create()
                .withClaim(Const.USER_CACHE_KEY_BODY, username)
                .withExpiresAt(Instant.now().plusSeconds(config.getExpiresec()))
                .sign(Algorithm.HMAC256(config.getSecretkey()));
        String key = Const.USER_CACHE_KEY_PREFIX + username;
        redisTemplate.opsForValue().set(key,jwtUser);
        return Result.success("Login Succeed.",token);
    }

    @Override
    public Result logout() {
        UsernamePasswordAuthenticationToken authentication
                = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String username = jwtUser.getUsername();
        String key = Const.USER_CACHE_KEY_PREFIX + username;
        redisTemplate.delete(key);
        return Result.success("Logout Success.");
    }
}
