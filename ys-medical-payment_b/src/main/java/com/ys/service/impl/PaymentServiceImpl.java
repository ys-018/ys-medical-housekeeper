package com.ys.service.impl;

import com.ys.dao.PaymentDao;
import com.ys.entity.Payment;
import com.ys.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    // 注入有两种 @Resource java 自带的注入，@Autowired 是 spring 的
    @Resource
    private PaymentDao paymentDao;

    @Override
    public int save(Payment payment) {
        return paymentDao.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
