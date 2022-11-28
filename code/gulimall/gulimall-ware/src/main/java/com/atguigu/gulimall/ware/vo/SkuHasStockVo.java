package com.atguigu.gulimall.ware.vo;
import lombok.Data;

/**
 * @program: gulimall
 * @description:
 * @author: LZ
 * @create: 2022-11-28 23:08
 **/

@Data
public class SkuHasStockVo {
    private Long skuId;
    private Boolean hasStock;
}
