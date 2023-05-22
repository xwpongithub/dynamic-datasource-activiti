package jp.smartcompany.dynamicdatasourceactiviti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReduceStockRequest {

    private Long productId;

    private Integer amount;

}
