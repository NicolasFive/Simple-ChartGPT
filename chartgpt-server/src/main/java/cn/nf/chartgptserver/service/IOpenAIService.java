package cn.nf.chartgptserver.service;

import cn.nf.chartgptserver.entity.ChartMessage;
import com.alibaba.fastjson2.JSONObject;

public interface IOpenAIService {
    JSONObject sendMessage(ChartMessage msg);
}
