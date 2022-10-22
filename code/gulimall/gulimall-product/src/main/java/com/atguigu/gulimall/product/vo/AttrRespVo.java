package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @program: gulimall
 * @description:
 * @author: LZ
 * @create: 2022-10-22 19:04
 **/
@Data
public class AttrRespVo extends AttrVo{

    private String catelogName;

    private String groupName;

    private Long[] catelogPath;
}
