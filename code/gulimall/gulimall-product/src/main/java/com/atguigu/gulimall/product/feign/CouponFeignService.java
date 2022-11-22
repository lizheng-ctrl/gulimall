package com.atguigu.gulimall.product.feign;

import com.atguigu.common.to.SkuReductionTo;
import com.atguigu.common.to.SpuBoundTo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lizheng
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {


    /**
     * 1、CouPonFeignService.saveSpuBounds(spuBoundTo)
     * 1) @RequestBody 将这个对象转成json
     * 2) 找到gulimall-coupon服务,给/coupon/spubounds/save 发送请求
     * 3）、对方服务收到请求、请求体里有json 数据
     *
     * @RequestBody SpuBoundsEntity spuBounds 将请求体的json 转为SpuBoundEntity
     * 只要json 数据模型是兼容的 。双方服务无需使用同一个to
     */
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
