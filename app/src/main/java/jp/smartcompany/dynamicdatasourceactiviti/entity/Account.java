package jp.smartcompany.dynamicdatasourceactiviti.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName(value = "account",schema = "public")
public class Account {

    @TableId
    private Long id;

    /**
     * 余额
     */
    private Double balance;

    private Date lastUpdateTime;
}
