package ${basePackage}.repository;

import ${basePackage}.model.entity.${aggregateName};
import ir.ipaam.baseclasses.BaseRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


/**
* Repository for ${aggregateName}.
* Inherits CRUD + Specification support from BaseRepository.
*/
@Repository
public interface ${aggregateName}Repository extends BaseRepository<${aggregateName}, UUID> {
// Custom query methods can be added here if needed
}
