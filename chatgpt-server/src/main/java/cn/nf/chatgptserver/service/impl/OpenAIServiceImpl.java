package cn.nf.chatgptserver.service.impl;

import cn.nf.chatgptserver.entity.ChatMessage;
import cn.nf.chatgptserver.service.IOpenAIService;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OpenAIServiceImpl implements IOpenAIService {
    @Value("${openai.authorization}")
    private String authorization;
    @Override
    public JSONObject sendMessage(ChatMessage msg) {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 10808)));
            builder.callTimeout(5, TimeUnit.MINUTES);
            builder.connectTimeout(1, TimeUnit.MINUTES);
            builder.readTimeout(3, TimeUnit.MINUTES);
            builder.writeTimeout(1, TimeUnit.MINUTES);
            OkHttpClient client = builder.build();

            MediaType mediaType = MediaType.parse("application/json");
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/chat/completions")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer "+authorization)
                    .post(okhttp3.RequestBody.create(mediaType, JSONObject.toJSONString(msg).replaceAll("\\n","")))
                    .build();

            Response response = client.newCall(request).execute();
            return JSONObject.parse(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ChatMessage msg=JSONObject.parseObject("{\"max_tokens\":1000,\"messages\":[{\"content\":\"how to build a springboot application?\",\"role\":\"user\"},{\"content\":\"To build a Spring Boot application, follow these steps:\\n\\n1. Install Java Development Kit (JDK) 8 or later.\\n\\n2. Install a Java Integrated Development Environment (IDE) like Eclipse, IntelliJ IDEA, or NetBeans.\\n\\n3. Create a new Spring Boot project in the IDE. You can do this by selecting the \\\"Spring Starter Project\\\" option and configuring the project settings like the project name, package name, and dependencies.\\n\\n4. Add the required dependencies for your use case. For example, if you are building a web application, you will need to add dependencies like Spring Web, Spring Data JPA (if you are working with databases), and Spring Security (if you are implementing security features).\\n\\n5. Write the code for your application, using the Spring Boot features and APIs as appropriate.\\n\\n6. Run and test your application to make sure it works as expected.\\n\\n7. Build your application by creating an executable JAR or WAR file, using tools like Maven or Gradle.\\n\\n8. Deploy your application to a server or cloud platform of your choice.\\n\\n9. Monitor and maintain your application, making updates and improvements as needed.\",\"role\":\"assistant\"},{\"content\":\"我刚刚说了什么\",\"role\":\"user\"}],\"model\":\"gpt-3.5-turbo\"}", ChatMessage.class);
        System.out.println(JSONObject.toJSONString(msg, JSONWriter.Feature.PrettyFormat));
    }
}
