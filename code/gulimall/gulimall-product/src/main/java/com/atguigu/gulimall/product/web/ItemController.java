package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.service.SkuInfoService;
import com.atguigu.gulimall.product.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    /**
     * 展示商品详情
     * @param skuId
     * @return
     */

    @Autowired
    private SkuInfoService skuInfoService;


    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) throws Exception{

        System.out.println("准备查询" + skuId + "详情");
        SkuItemVo vos = skuInfoService.item(skuId);
        model.addAttribute("item",vos);
        return "item";

    }
}
