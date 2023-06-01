package cn.nf.chatgptserver.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Example:
 {
 "model":"gpt-3.5-turbo",
 "max_tokens":100,
 "messages":[
 {"role": "system", "content": "You are a helpful assistant."},
 {"role": "user", "content": "Who won the world series in 2020?"}
 ]
 }
 */
@Data
public class ChatMessage implements Serializable {
    private String model;
    private Integer max_tokens;
    private Integer temperature;
    private Integer top_p;
    private Integer n;
    private Integer presence_penalty;
    private Integer frequency_penalty;
    private List<Message> messages;
    @Data
    public static class Message{
        private String role;
        private String content;
    }
}
