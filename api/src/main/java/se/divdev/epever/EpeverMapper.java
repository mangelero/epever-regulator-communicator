package se.divdev.epever;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EpeverMapper {
    int address();

    Class<? extends DataMapper> clazz() default DataMapper.Denominator100Mapper.class;
}
