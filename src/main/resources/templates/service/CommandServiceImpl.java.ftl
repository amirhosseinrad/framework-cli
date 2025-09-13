package ${basePackage}.service.command;

import ${basePackage}.model.dto.${aggregateName}Dto;
import ${basePackage}.mapper.${aggregateName}Mapper;
import ${basePackage}.model.entity.${aggregateName};
import ${basePackage}.repository.${aggregateName}Repository;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * Command service implementation for ${aggregateName}.
 */
@Service
public class ${aggregateName}CommandServiceImpl implements ${aggregateName}CommandService {

    private final ${aggregateName}Repository repository;
    private final ${aggregateName}Mapper mapper;

    public ${aggregateName}CommandServiceImpl(${aggregateName}Repository repository,
                                             ${aggregateName}Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ${aggregateName}Dto save(${aggregateName}Dto dto) {
        ${aggregateName} entity = mapper.toEntity(dto);
        ${aggregateName} saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
