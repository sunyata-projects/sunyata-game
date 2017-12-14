package org.sunyata.game.state.manager.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface OctopusEvent {
    String value() default "";

    String description() default "";

    String version() default "";

    String serviceName() default "";

    String eventName() default "";

    String eventId() default "";

    int unicast() default 0;
}

