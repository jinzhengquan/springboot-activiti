package springboot.activiti

import springboot.activiti.core.annotation.EnableGlobalExceptionHandler
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableGlobalExceptionHandler
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
