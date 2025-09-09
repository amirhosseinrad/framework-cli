package ${basePackage}.service.impl;

import ${basePackage}.model.entity.${aggregateName};
import ${basePackage}.model.dto.${aggregateName}Dto;
import ${basePackage}.mapper.${aggregateName}Mapper;
import ${basePackage}.repository.${aggregateName}Repository;
import ${basePackage}.service.${aggregateName}Service;
import ir.ipaam.baseclasses.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* ${aggregateName} service implementation extending BaseServiceImpl.
*/
@Service
public class ${aggregateName}ServiceImpl
extends BaseServiceImpl<${aggregateName}, ${aggregateName}Dto, ${aggregateName}Repository>
implements ${aggregateName}Service {

private final ${aggregateName}Mapper mapper;

public ${aggregateName}ServiceImpl(${aggregateName}Repository repository,
${aggregateName}Mapper mapper) {
super(repository);
this.mapper = mapper;
}

@Override
protected ${aggregateName} toEntity(${aggregateName}Dto dto) {
return mapper.toEntity(dto);
}

@Override
protected ${aggregateName}Dto toDto(${aggregateName} entity) {
return mapper.toDto(entity);
}
}
