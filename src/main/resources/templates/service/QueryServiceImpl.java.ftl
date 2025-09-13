package ${basePackage}.service.query;

import ${basePackage}.model.dto.${aggregateName}Dto;
import ${basePackage}.mapper.${aggregateName}Mapper;
import ${basePackage}.model.entity.${aggregateName};
import ${basePackage}.repository.${aggregateName}Repository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Query service implementation for ${aggregateName}.
 */
@Service
public class ${aggregateName}QueryServiceImpl implements ${aggregateName}QueryService {

    private final ${aggregateName}Repository repository;
    private final ${aggregateName}Mapper mapper;

    public ${aggregateName}QueryServiceImpl(${aggregateName}Repository repository,
                                           ${aggregateName}Mapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ${aggregateName}Dto findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    @Override
    public List<${aggregateName}Dto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<${aggregateName}Dto> search(String keyword) {
        // Placeholder search implementation
        return findAll();
    }
}
