package cn.nf.chartgptserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.nf.chartgptserver.mapper")
public class ChartgptServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChartgptServerApplication.class, args);
	}
}
