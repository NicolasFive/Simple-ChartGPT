package cn.nf.chatgptserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.nf.chatgptserver.mapper")
public class ChatgptServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatgptServerApplication.class, args);
	}
}
