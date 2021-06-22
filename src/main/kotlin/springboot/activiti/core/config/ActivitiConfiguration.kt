package springboot.activiti.core.config

import org.activiti.engine.*
import org.activiti.spring.ProcessEngineFactoryBean
import org.activiti.spring.SpringProcessEngineConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource


@Configuration
class ActivitiConfiguration {
    @Autowired
    private val platformTransactionManager: PlatformTransactionManager? = null

    @Autowired
    private val dataSource: DataSource? = null

    @Bean
    fun processEngineConfiguration(): SpringProcessEngineConfiguration? {
        val processEngineConfiguration = SpringProcessEngineConfiguration()
        processEngineConfiguration.dataSource = dataSource
        processEngineConfiguration.transactionManager = platformTransactionManager
        processEngineConfiguration.databaseSchemaUpdate = "true"
        processEngineConfiguration.activityFontName = "宋体"
        processEngineConfiguration.labelFontName = "宋体"
        return processEngineConfiguration
    }

    @Bean
    fun processEngine(@Qualifier("processEngineConfiguration") processEngineConfiguration: SpringProcessEngineConfiguration?): ProcessEngineFactoryBean? {
        val processEngineFactoryBean = ProcessEngineFactoryBean()
        processEngineFactoryBean.processEngineConfiguration = processEngineConfiguration
        return processEngineFactoryBean
    }

    @Bean
    fun repositoryService(@Qualifier("processEngine") processEngine: ProcessEngine): RepositoryService? {
        return processEngine.repositoryService
    }

    @Bean
    fun runtimeService(@Qualifier("processEngine") processEngine: ProcessEngine): RuntimeService? {
        return processEngine.runtimeService
    }

    @Bean
    fun taskService(@Qualifier("processEngine") processEngine: ProcessEngine): TaskService? {
        return processEngine.taskService
    }

    @Bean
    fun identityService(@Qualifier("processEngine") processEngine: ProcessEngine): IdentityService? {
        return processEngine.identityService
    }

    @Bean
    fun historyService(@Qualifier("processEngine") processEngine: ProcessEngine): HistoryService? {
        return processEngine.historyService
    }
}