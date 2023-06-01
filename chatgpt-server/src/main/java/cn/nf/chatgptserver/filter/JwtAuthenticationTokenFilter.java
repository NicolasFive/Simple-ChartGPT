package cn.nf.chatgptserver.filter;

import cn.nf.chatgptserver.config.Const;
import cn.nf.chatgptserver.entity.JwtUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    public RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = request.getHeader(Const.AUTH_HEADER);
            if (StringUtils.isEmpty(token)) {
                return;
            }
            DecodedJWT jwt=JWT.decode(token);
            String body = jwt.getClaim(Const.USER_CACHE_KEY_BODY).asString();
            String key = Const.USER_CACHE_KEY_PREFIX + body;
            if (jwt.getExpiresAt().before(new Date())){
                redisTemplate.delete(key);
                return;
            }
            JwtUser loginUser = (JwtUser) redisTemplate.opsForValue().get(key);
            if (loginUser == null) {
                return;
            }
            request.setAttribute(Const.USER_CACHE_KEY,key);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }catch (Exception e){
            log.error("Auth check error: {}",e.getMessage());
        } finally{
            filterChain.doFilter(request, response);
        }
    }
}
