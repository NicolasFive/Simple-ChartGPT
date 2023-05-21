package cn.nf.chartgptserver.service;


import cn.nf.chartgptserver.entity.DBUser;
import cn.nf.chartgptserver.entity.Result;

public interface ILoginService {
    Result login(DBUser user);
    Result logout();
}
