package ${basePackage}.service.command;

import ${basePackage}.model.dto.${aggregateName}Dto;
import java.util.UUID;

/**
 * Command service interface for ${aggregateName}.
 * Provides write operations.
 */
public interface ${aggregateName}CommandService {
    ${aggregateName}Dto save(${aggregateName}Dto dto);
    void deleteById(UUID id);
}
