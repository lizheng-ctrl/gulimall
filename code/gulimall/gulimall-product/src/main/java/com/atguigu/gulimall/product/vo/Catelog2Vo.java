package com.atguigu.gulimall.product.vo;

import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: gulimall
 * @description:
 * @author: LZ
 * @create: 2023-03-17 22:07
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catelog2Vo {
    //1级父分类id
    private String catalog1Id;
    //三级子分类
    private List<Catelog3Vo> catalog3List;
    private String id;
    private String name;


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Catelog3Vo{

        private String catalog2Id;

        private String id;

        private String name;
    }

}


