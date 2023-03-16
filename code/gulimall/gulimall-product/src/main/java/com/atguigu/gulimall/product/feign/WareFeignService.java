package com.atguigu.gulimall.product.feign;
import com.atguigu.common.to.SkuHasStockVo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-06 15:50
 **/

@FeignClient("gulimall-ware")
public interface WareFeignService {

    /***
     *1。 R 设计的时候可以加上泛型
     *2.  直接返回我们想要的结果
     *3， 自己封装解析结果
     */

    @PostMapping(value = "/ware/waresku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);

}
