package jp.smartcompany.dynamicdatasourceactiviti.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Account {

    @TableId
    private Long id;

    /**
     * 余额
     */
    private Double balance;

    private Date lastUpdateTime;
}
