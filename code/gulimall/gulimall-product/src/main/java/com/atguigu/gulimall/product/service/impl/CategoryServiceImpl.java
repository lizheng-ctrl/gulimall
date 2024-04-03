package com.atguigu.gulimall.product.service.impl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.config.RedisCache;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {


    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    @Resource
    private RedisCache redisCache;


    @Resource
    private Redisson redisson;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<CategoryEntity> listWithTree() {
        //查询出所有的商品列表
        List<CategoryEntity> entities = baseMapper.selectList(null);
        List<CategoryEntity> levelMenus = entities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == 0;
        }).map((menu) -> {
            menu.setChildren(getChildrenMenus(menu, entities));
            return menu;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());

        return levelMenus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1检查当前删除的菜单,是否被别的地方占用
        baseMapper.deleteBatchIds(asList);
    }


    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = this.findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新所有关联的数据
     *
     * @param category
     * @return void
     * @author lizheng
     * @date 2022/10/22 11:46
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        redisCache.deleteObject("catalogJsonData");
    }

    @Override
    public List<CategoryEntity> getLevel1Categories() {
        List<CategoryEntity> entities = this.baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return entities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        String catalogJsonData = redisCache.getCacheObject("catalogJsonData");
        if (StringUtils.isEmpty(catalogJsonData)) {
            RLock lock = redisson.getLock("catelogjson-lock");
            lock.lock();
            try {
                catalogJsonData = redisCache.getCacheObject("catalogJsonData");
                if (StringUtils.isEmpty(catalogJsonData)) {
                    Map<String, List<Catelog2Vo>> catalogJsonFromDb = getCatalogJsonFromDb();
                    catalogJsonData = JSON.toJSONString(catalogJsonFromDb);
                    redisCache.setCacheObject("catalogJsonData", catalogJsonData);
                    return catalogJsonFromDb;
                }
                Map<String, List<Catelog2Vo>> data = JSON.parseObject(catalogJsonData, new TypeReference<Map<String, List<Catelog2Vo>>>() {
                });
                return data;
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }
        Map<String, List<Catelog2Vo>> data = JSON.parseObject(catalogJsonData, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
        return data;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDb() {

        List<CategoryEntity> entities = this.baseMapper.selectList(null);
        Map<Integer, List<CategoryEntity>> categoryMap = entities.stream().collect(Collectors.groupingBy(CategoryEntity::getCatLevel));

        //获取一级分类
        List<CategoryEntity> category1 = categoryMap.get(1);
        List<CategoryEntity> category2 = categoryMap.get(2);
        List<CategoryEntity> category3 = categoryMap.get(3);


        Map<Long, List<CategoryEntity>> categoryMap2 = category2.stream().collect(Collectors.groupingBy(CategoryEntity::getParentCid));
        Map<Long, List<CategoryEntity>> categoryMap3 = category3.stream().collect(Collectors.groupingBy(CategoryEntity::getParentCid));

        Map<String, List<Catelog2Vo>> map = new HashMap<>();
        category1.forEach(categoryEntity -> {
            Long catId = categoryEntity.getCatId();
            List<CategoryEntity> entities1 = categoryMap2.get(catId);
            List<Catelog2Vo> catelog2Vos = new ArrayList<>();
            entities1.forEach(item->{
                Catelog2Vo catelog2Vo = new Catelog2Vo();
                catelog2Vo.setCatalog1Id(catId.toString());
                catelog2Vo.setName(item.getName());
                catelog2Vo.setId(item.getCatId().toString());

                //组装三级分类
                List<CategoryEntity> entities2 = categoryMap3.get(item.getCatId());
                ArrayList<Catelog2Vo.Catelog3Vo> catelog3Vos = new ArrayList<>();
                if(entities2!=null){
                    entities2.forEach(item1->{
                        Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo();
                        catelog3Vo.setCatalog2Id(item.getCatId().toString());
                        catelog3Vo.setId(item1.getCatId().toString());
                        catelog3Vo.setName(item1.getName());
                        catelog3Vos.add(catelog3Vo);
                    });
                    catelog2Vo.setCatalog3List(catelog3Vos);
                }

                catelog2Vos.add(catelog2Vo);
                map.put(catId.toString(),catelog2Vos);
            });
        });
        return map;
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        //收集当前节点id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            this.findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }

    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrenMenus(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> childrenMenus = all.stream().filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId())).map(categoryEntity -> {
            categoryEntity.setChildren(getChildrenMenus(categoryEntity, all));
            return categoryEntity;
        }).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
        return childrenMenus;
    }


}