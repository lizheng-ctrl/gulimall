package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @program: gulimall
 * @description: 首页请求
 * @author: LZ
 * @create: 2023-03-17 21:24
 **/
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;



    @GetMapping({"/","/index.html"})
    public String indexPage(Model model){
        List<CategoryEntity> categoryEntityList =  categoryService.getLevel1Categories();
        model.addAttribute("categories",categoryEntityList);
        return "index";
    }



    @ResponseBody
    @GetMapping("/index/json/catalog.json")
    public Map<String,List<Catelog2Vo>> getCatalogJson(){
        return categoryService.getCatalogJson();
    }

}
