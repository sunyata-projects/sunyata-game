package org.sunyata.game.majiang.core.models.majiang.actions;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String value() default "";

    OperationWhen operationWhen() default OperationWhen.whenOtherChuPai;
}

