package ${basePackage}.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ${aggregateName}Dto {

<#list fields as f>
    private ${f.type} ${f.name};
</#list>

}
