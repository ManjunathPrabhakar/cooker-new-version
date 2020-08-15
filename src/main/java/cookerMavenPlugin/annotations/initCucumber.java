package cookerMavenPlugin.annotations;

import java.lang.annotation.*;

/**
 * @author Manjunath-PC
 * @created 15/08/2020
 * @project custom-annotations
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface initCucumber {
    String propertiesPath();
}