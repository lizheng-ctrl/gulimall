package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: gulimall
 * @description:
 * @author: LZ
 * @create: 2022-11-07 20:50
 **/
@Data
public class SpuBoundTo {

    private Long spuId;

    private BigDecimal buyBounds;

    private BigDecimal growBounds;
}
