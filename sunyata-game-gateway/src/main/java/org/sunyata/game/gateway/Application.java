package org.sunyata.game.gateway; /**
 * Created by leo on 17/4/18.
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.sunyata.game.state.manager.config.EnableGameServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableGameServer
public class Application {
    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}
