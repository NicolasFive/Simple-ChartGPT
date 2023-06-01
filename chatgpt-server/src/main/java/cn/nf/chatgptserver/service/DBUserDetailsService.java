package cn.nf.chatgptserver.service;

import cn.nf.chatgptserver.entity.JwtUser;
import cn.nf.chatgptserver.entity.DBUser;
import cn.nf.chatgptserver.mapper.DBUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class DBUserDetailsService implements UserDetailsService {
    private DBUserMapper dbUserMapper;

    public DBUserDetailsService(DBUserMapper dbUserMapper) {
        this.dbUserMapper = dbUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<DBUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DBUser::getStatus, 0)
        .and(w->{
            w.eq(DBUser::getEmail, username)
                    .or()
                    .eq(DBUser::getPhone, username);
        });
        DBUser user=dbUserMapper.selectOne(queryWrapper);
        if (user==null) {
            throw new UsernameNotFoundException("");
        }
        List<String> permissions = new ArrayList<>(Arrays.asList("test"));
        return new JwtUser(user, permissions);
    }
}
