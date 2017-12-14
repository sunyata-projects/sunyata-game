package org.sunyata.game.state.manager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.*;

/**
 * Created by leo on 17/11/1.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableScheduling
@Configuration
@ComponentScan("org.sunyata.game.state")
@Import({RedisConfig.class, CommandStateConfig.class})
public @interface EnableOctopusState {

}
