package com.zhengqing.order.api;

import com.zhengqing.order.entity.Order;
import com.zhengqing.order.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 订单api
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/01/13 10:11
 */
@Slf4j
@RestController
@RequestMapping("/api/order")
@Api(tags = {"订单api"})
@RefreshScope // 实现配置自动更新
public class OrderController {

    @Autowired
    private IOrderService orderService;

    // SPEL
    @Value("${spring.application.name}")
    private String name;

    @GetMapping("/hello")
    public String hello() {
        // int result = 1 / 0;
        return "《order》:" + name;
    }

    @GetMapping("")
    @ApiOperation("详情")
    public Order listPage(@RequestParam Long orderId) {
        return this.orderService.getById(orderId);
    }

    @PostMapping("")
    @ApiOperation("保存数据")
    @Transactional(rollbackFor = Exception.class)
    public String insertData() {
        boolean save = this.orderService.saveOrUpdate(Order.builder()
                .orderId(null)
                .userId(1L)
                .payMoney(100)
                .payTime(new Date())
                .remark("seata-order")
                .build());
        return "SUCCESS";
    }

}
