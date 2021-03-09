package com.ys.dao;

import com.ys.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author
 * @ClassName:
 * @Description:
 * @date
 */

@Mapper
public interface PaymentDao {

    public int save(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
