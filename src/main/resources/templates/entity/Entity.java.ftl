package ${basePackage}.model.entity;

import ir.ipaam.baseclasses.BaseEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "${entityName?lower_case}")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ${entityName} extends BaseEntity implements Serializable {


    <#-- Loop over fields -->
    <#list fields as f>
        @Column
        private ${f.type} ${f.name};
    </#list>

}