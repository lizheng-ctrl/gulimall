package com.atguigu.gulimall.product.feign;

import com.atguigu.common.to.es.SkuEsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.atguigu.common.utils.R;
import java.util.List;

/**
 * @program: gulimall
 * @description:
 * @author: LZ
 * @create: 2023-03-16 16:27
 **/
@FeignClient("gulimall-search")
public interface SearchFeignService {

    @PostMapping(value = "/search/save/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
