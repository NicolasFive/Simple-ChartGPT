package cn.nf.chatgptserver.service;


import cn.nf.chatgptserver.entity.DBUser;
import cn.nf.chatgptserver.entity.Result;

public interface ILoginService {
    Result login(DBUser user);
    Result logout();
}
