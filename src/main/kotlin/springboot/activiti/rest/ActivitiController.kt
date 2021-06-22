package springboot.activiti.rest

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/activiti")
class ActivitiController {

    @GetMapping("/picture/create")
    fun create(model: Model, modelId: String): Mono<String> {
        model.addAttribute("modelId", modelId);
        return Mono.create { monoSink -> monoSink.success("modeler") };
    }

}