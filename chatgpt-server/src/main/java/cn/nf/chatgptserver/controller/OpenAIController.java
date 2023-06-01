package cn.nf.chatgptserver.controller;

import cn.nf.chatgptserver.entity.ChatMessage;
import cn.nf.chatgptserver.entity.Result;
import cn.nf.chatgptserver.service.IOpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("openai")
public class OpenAIController {


    private IOpenAIService openAIService;

    public OpenAIController(IOpenAIService openAIService) {
        this.openAIService = openAIService;
    }

//    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("sendMessage")
    public Result sendMessage(@RequestBody ChatMessage msg){
        log.info("chat {}",msg);
        return Result.success(openAIService.sendMessage(msg));
    }
}
