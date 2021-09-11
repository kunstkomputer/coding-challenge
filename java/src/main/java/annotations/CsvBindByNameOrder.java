package annotations;

import java.lang.annotation.*;

/*
Content of this files is a 1:1 copy of this gist: https://gist.github.com/ammmze/ec0334d107cb63c586ffd8fc51ec5757
See Attributions.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CsvBindByNameOrder {
    String[] value();
}
