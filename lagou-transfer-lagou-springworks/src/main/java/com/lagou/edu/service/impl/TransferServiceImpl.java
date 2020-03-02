package com.lagou.edu.service.impl;

import com.lagou.edu.aop.annotation.MyAutowired;
import com.lagou.edu.aop.annotation.MyService;
import com.lagou.edu.aop.annotation.MyTransational;
import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * @author wangdm
 */
@MyService
public class TransferServiceImpl implements TransferService {

    @MyAutowired
    private AccountDao jdbcTemplateDaoImpl;

    @MyTransational
    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        Account from = jdbcTemplateDaoImpl.queryAccountByCardNo(fromCardNo);
        Account to = jdbcTemplateDaoImpl.queryAccountByCardNo(toCardNo);
        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);
        jdbcTemplateDaoImpl.updateAccountByCardNo(to);
//            int c = 1/0;
        jdbcTemplateDaoImpl.updateAccountByCardNo(from);
    }
}
