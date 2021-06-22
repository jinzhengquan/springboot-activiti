package springboot.activiti.query

import com.example.demo.domain.exceptions.DomainException
import com.example.demo.domain.exceptions.ErrorCodes.Companion.ACTIVITI_MODEL_NOT_EXIST
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import org.activiti.engine.RepositoryService
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ActivitiModelQuery {
    private val logger = LoggerFactory.getLogger(ActivitiModelQuery::class.java);

    @Autowired
    private lateinit var repositoryService: RepositoryService;

    fun findActivitiModel(modelId: String): ObjectNode {
        val objectMapper = ObjectMapper();
        var modelNode: ObjectNode = objectMapper.createObjectNode();
        var model = repositoryService.getModel(modelId);
        takeIf { model != null }?.apply {
            try {
                if (StringUtils.isNotEmpty(model.metaInfo)) {
                    modelNode = objectMapper.readTree(model.metaInfo) as ObjectNode
                } else {
                    modelNode.put("name", model.name);
                }
                modelNode.put("modelId", model.id);
                modelNode.put("model", editorJsonNode(model.id, objectMapper));
            } catch (e: Exception) {
                logger.error("Activiti model not exist", e)
                throw DomainException(ACTIVITI_MODEL_NOT_EXIST, modelId);
            }
        }
        return modelNode;
    }

    private fun editorJsonNode(id:String, objectMapper:ObjectMapper):ObjectNode {
        return objectMapper.readTree(String(repositoryService.getModelEditorSource(id))) as ObjectNode;
    }

}