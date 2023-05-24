package jp.smartcompany.dynamicdatasourceactiviti.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName(value = "product",schema = "public")
public class Product {

    @TableId
    private Integer id;
    /**
     * 价格
     */
    private Double price;
    /**
     * 库存
     */
    private Integer stock;

    private Date lastUpdateTime;
}
