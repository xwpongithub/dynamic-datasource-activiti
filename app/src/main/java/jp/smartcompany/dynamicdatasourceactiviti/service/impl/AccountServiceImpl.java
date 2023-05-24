package jp.smartcompany.dynamicdatasourceactiviti.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import io.seata.core.context.RootContext;
import jp.smartcompany.dynamicdatasourceactiviti.entity.Account;
import jp.smartcompany.dynamicdatasourceactiviti.mapper.AccountMapper;
import jp.smartcompany.dynamicdatasourceactiviti.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    /**
     * 事务传播特性设置为 REQUIRES_NEW 开启新的事务
     */
    @DS("account")
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void reduceBalance(Long userId, Double price) {
        log.info("=============ACCOUNT START=================");
        log.info("当前 XID: {}", RootContext.getXID());

        Account account = accountMapper.selectById(userId);
        Assert.notNull(account, "用户不存在");
        Double balance = account.getBalance();
        log.info("下单用户{}余额为 {},商品总价为{}", userId, balance, price);

        if (balance < price) {
            log.warn("用户 {} 余额不足，当前余额:{}", userId, balance);
            throw new RuntimeException("余额不足");
        }
        log.info("开始扣减用户 {} 余额", userId);
        double currentBalance = account.getBalance() - price;
        account.setBalance(currentBalance);
        accountMapper.updateById(account);
        log.info("扣减用户 {} 余额成功,扣减后用户账户余额为{}", userId, currentBalance);
        log.info("=============ACCOUNT END=================");
    }
}
