package springboot.activiti.core.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.tags.Tag
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {
    @Bean
    fun openApi(): OpenAPI? {
        return OpenAPI()
            .info(
                Info()
                    .title("springboot-activiti")
            )
            .tags(
                listOf(
                    Tag().name("springboot-activiti")
                )
            )
    }
}