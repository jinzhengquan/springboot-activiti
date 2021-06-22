package springboot.activiti.rest

import com.example.demo.domain.exceptions.DomainException
import com.example.demo.domain.exceptions.ErrorCodes.Companion.ACTIVITI_MODEL_STENCIL_ERROR
import com.fasterxml.jackson.databind.node.ObjectNode
import springboot.activiti.query.ActivitiModelQuery
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/activiti")
class ModelQueryController {

    private val logger = LoggerFactory.getLogger(ModelQueryController::class.java)

    @Autowired
    private lateinit var activitiModelQuery: ActivitiModelQuery;

    @GetMapping("/model/{modelId}/json")
    fun findActivitiModel(@PathVariable modelId: String): ObjectNode? {
        return activitiModelQuery.findActivitiModel(modelId);
    }

    @GetMapping("/editor/stencilset")
    fun getStencilset(): String? {
        val stencilsetStream = this.javaClass.classLoader.getResourceAsStream("stencilset.json")
        return try {
            IOUtils.toString(stencilsetStream, Charsets.UTF_8.toString());
        } catch (e: java.lang.Exception) {
            logger.error("Error while loading stencil set");
            throw DomainException(ACTIVITI_MODEL_STENCIL_ERROR);
        }
    }
}