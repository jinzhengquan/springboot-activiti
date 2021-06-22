package springboot.activiti.domain.application

import com.example.demo.domain.exceptions.DomainException
import com.example.demo.domain.exceptions.ErrorCodes.Companion.ACTIVITI_MODEL_CREATE_FAIL
import com.example.demo.domain.exceptions.ErrorCodes.Companion.ACTIVITI_MODEL_PICTURE_ADD_FAIL
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import springboot.activiti.domain.application.cmd.AddActivitiModelCmd
import org.activiti.editor.constants.ModelDataJsonConstants
import org.activiti.engine.RepositoryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import java.nio.charset.Charset

@Service
class ActivitiModelApplicationService {
    private val logger = LoggerFactory.getLogger(ActivitiModelApplicationService::class.java)

    @Autowired
    private lateinit var repositoryService: RepositoryService;

    fun addActivitiModel(cmd: AddActivitiModelCmd) {
        try {
            val objectMapper = ObjectMapper();
            val nodeInitInfo: ObjectNode = creteNodeInitInfo(objectMapper);
            val modelMetaInfo: ObjectNode = createMetaInfo(objectMapper, cmd.name, cmd.description);
            val modelData = repositoryService.newModel();
            modelData.metaInfo = modelMetaInfo.toString();
            modelData.name = cmd.name;
            modelData.key = cmd.modelCode;
            repositoryService.saveModel(modelData)
            repositoryService.addModelEditorSource(
                modelData.id,
                nodeInitInfo.toString().toByteArray(Charset.forName("utf-8"))
            );
        } catch (ex: Exception) {
            logger.error("activiti model create fail");
            throw DomainException(ACTIVITI_MODEL_CREATE_FAIL);
        }
    }

    fun addActivitiModelPicture(modelId: String, values: MultiValueMap<String, String>) {
        val model = repositoryService.getModel(modelId);
        takeIf { model != null }?.apply {
            try {
                val objectMapper: ObjectMapper = ObjectMapper();
                val modelJson = objectMapper.readTree(model.metaInfo) as ObjectNode;
                modelJson.put(ModelDataJsonConstants.MODEL_NAME, values.getFirst("name"));
                modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION, values.getFirst("description"));
                model.metaInfo = modelJson.toString();
                model.name = values.getFirst("name");
                repositoryService.saveModel(model);
                repositoryService.addModelEditorSource(
                    model.id,
                    values.getFirst("json_xml")?.toByteArray(Charset.forName("gbk"))
                );
            } catch (ex: Exception) {
                throw DomainException(ACTIVITI_MODEL_PICTURE_ADD_FAIL);
            }
        };
    }

    private fun creteNodeInitInfo(objectMapper: ObjectMapper): ObjectNode {
        val nodeInitInfo = objectMapper.createObjectNode();
        nodeInitInfo.put("id", "canvas");
        nodeInitInfo.put("resourceId", "canvas");
        nodeInitInfo.put(
            "stencilset",
            objectMapper.createObjectNode().put("namespace", "http://b3mn.org/stencilset/bpmn2.0#")
        );
        return nodeInitInfo;
    }

    private fun createMetaInfo(objectMapper: ObjectMapper, name: String, description: String): ObjectNode {
        val modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        return modelObjectNode;
    }

}