package cn.rs.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages="cn.rs.blog.dao")
public class RsBlogApplication {
	public static void main(String[] args) {
		SpringApplication.run(RsBlogApplication.class, args);
	}

}
