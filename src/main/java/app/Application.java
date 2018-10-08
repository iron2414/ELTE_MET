package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import util.AppErrorController;

//import util.libwebsocket.protocol.websocket.client.WebsocketClient;


@SpringBootApplication
@ComponentScan(basePackages = {"config", "app"})
@EntityScan(basePackages = {"app.entity"})
@EnableJpaRepositories(basePackages = {"config", "app"})
@EnableTransactionManagement
public class Application {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Bean
    public AppErrorController appErrorController() {
        return new AppErrorController(errorAttributes);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);

//        WebsocketClient clientSocket = new WebsocketClient("localhost", 1234);
        //clientSocket.connect();
    }
}