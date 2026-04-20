package wiki.darius;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McpClientApplication {
    public static void main(String[] args) {
        // 禁用JDK HTTP Client内部连接池，防止SSE重连时异常
        //System.setProperty("jdk.httpclient.connectionPoolSize", "0");
        //System.setProperty("jdk.httpclient.keepalive.timeout", "0");
        SpringApplication.run(McpClientApplication.class, args);
    }
}