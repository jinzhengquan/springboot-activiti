package springboot.activiti.core.annotation

import springboot.activiti.core.exceptions.ExceptionHandlerController
import org.springframework.context.annotation.Import
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Import(
    ExceptionHandlerController::class
)
annotation class EnableGlobalExceptionHandler
