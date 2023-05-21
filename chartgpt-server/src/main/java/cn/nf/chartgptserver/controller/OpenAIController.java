package cn.nf.chartgptserver.controller;

import cn.nf.chartgptserver.entity.ChartMessage;
import cn.nf.chartgptserver.entity.Result;
import cn.nf.chartgptserver.service.IOpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Result sendMessage(@RequestBody ChartMessage msg){
        log.info("chart {}",msg);
        return Result.success(openAIService.sendMessage(msg));
    }
}
