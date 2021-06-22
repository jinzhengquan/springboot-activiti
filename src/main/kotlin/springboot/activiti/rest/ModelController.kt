package springboot.activiti.rest

import springboot.activiti.domain.application.ActivitiModelApplicationService
import springboot.activiti.domain.application.cmd.AddActivitiModelCmd
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/activiti")
class ModelController {

    @Autowired
    private lateinit var activitiModelApplicationService: ActivitiModelApplicationService;

    @PostMapping("/add/model")
    fun addActivitiModel(@Valid @RequestBody cmd: AddActivitiModelCmd) {
        activitiModelApplicationService.addActivitiModel(cmd);
    }

    @PutMapping("/model/{modelId}/save")
    fun saveModel(@PathVariable modelId: String, @RequestBody values: MultiValueMap<String, String>) {
        activitiModelApplicationService.addActivitiModelPicture(modelId, values);
    }

}