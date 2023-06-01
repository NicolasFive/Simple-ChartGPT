package cn.nf.chatgptserver.service;

import cn.nf.chatgptserver.entity.ChatMessage;
import com.alibaba.fastjson2.JSONObject;

public interface IOpenAIService {
    JSONObject sendMessage(ChatMessage msg);
}
