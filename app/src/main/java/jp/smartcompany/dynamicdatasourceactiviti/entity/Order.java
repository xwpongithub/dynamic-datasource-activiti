package jp.smartcompany.dynamicdatasourceactiviti.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.smartcompany.dynamicdatasourceactiviti.common.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@TableName(value = "p_order",schema = "public")
public class Order {

    @TableId
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 订单状态
     */
    private OrderStatus status;
    /**
     * 数量
     */
    private Integer amount;
    /**
     * 总金额
     */
    private Double totalPrice;
}
