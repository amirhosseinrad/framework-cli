package ${basePackage}.service.query;

import ${basePackage}.model.dto.${aggregateName}Dto;
import java.util.List;
import java.util.UUID;

/**
 * Query service interface for ${aggregateName}.
 * Provides read operations.
 */
public interface ${aggregateName}QueryService {
    ${aggregateName}Dto findById(UUID id);
    List<${aggregateName}Dto> findAll();
    List<${aggregateName}Dto> search(String keyword);
}
