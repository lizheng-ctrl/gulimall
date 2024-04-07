package com.atguigu.gulimall.search.controller;

import com.atguigu.gulimall.search.service.MallSearchService;
import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {


    @Autowired
    private MallSearchService mallSearchService;
    /**
     * 将页面提交过来的所有请求查询参数封装成指定的对象
     * @param searchParam
     * @return
     */

    @GetMapping("/search.html")
    public String searchPage(SearchParam searchParam, Model model){
        //1. 根据传递来的页面的查询参数,去es中检索商品
        SearchResult response = mallSearchService.search(searchParam);
        model.addAttribute("result",response);
        return "search";
    }


}
