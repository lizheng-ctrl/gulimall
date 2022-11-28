package com.atguigu.common.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: gulimall
 * @description: sku 在es 中的数据模型
 * @author: LZ
 * @create: 2022-11-28 21:54
 **/
@Data
public class SkuEsModel {

    private Long skuId;

    private Long spuId;

    private String skuTitle;

    private BigDecimal skuPrice;

    private String skuImg;

    private Long saleCount;

    private Boolean hasStock;

    private Long hotScore;

    private Long brandId;

    private Long catalogId;

    private String brandName;

    private String brandImg;

    private String catalogName;

    private List<Attrs> attrs;

    @Data
    public static class Attrs {

        private Long attrId;

        private String attrName;

        private String attrValue;

    }

}
