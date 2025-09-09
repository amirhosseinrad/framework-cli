package ${basePackage}.mapper;

import ${basePackage}.model.entity.${aggregateName};
import ${basePackage}.model.dto.${aggregateName}Dto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ${aggregateName}Mapper {

${aggregateName}Mapper INSTANCE = Mappers.getMapper(${aggregateName}Mapper.class);

${aggregateName}Dto toDto(${aggregateName} entity);
${aggregateName} toEntity(${aggregateName}Dto dto);

List<${aggregateName}Dto> toDtoList(List<${aggregateName}> entities);
List<${aggregateName}> toEntityList(List<${aggregateName}Dto> dtos);

}