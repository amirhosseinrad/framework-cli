package ${basePackage}.controller;

import ${basePackage}.model.dto.${aggregateName}Dto;
import ${basePackage}.service.${aggregateName}Service;
import ir.ipaam.baseclasses.BaseController;
import org.springframework.web.bind.annotation.*;

/**
* REST Controller for ${aggregateName}.
* Inherits CRUD + search endpoints from BaseController.
*/
@RestController
@RequestMapping("/api/${serviceName}")
public class ${aggregateName}Controller
extends BaseController<${aggregateName}Dto, ${aggregateName}Service> {

public ${aggregateName}Controller(${aggregateName}Service service) {
super(service);
}
}
