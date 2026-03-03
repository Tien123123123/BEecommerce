package com.ecommerce.be.ecommercebe.dto.response;

import com.ecommerce.be.ecommercebe.dto.BaseValidate;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponse implements BaseValidate {
    private Long id;
    private String name;
    private String value;
}
