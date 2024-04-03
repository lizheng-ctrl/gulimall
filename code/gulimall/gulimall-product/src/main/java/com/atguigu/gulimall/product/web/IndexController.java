package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.config.RedisCache;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catelog2Vo;
import org.redisson.Redisson;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

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

    @Resource
    private Redisson redisson;

    @Autowired
    private RedisCache redisCache;



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


    @RequestMapping("/hello")
    @ResponseBody
    public String hello() throws InterruptedException {
        RLock lock = redisson.getLock("my-lock");
        lock.lock();
        long id = Thread.currentThread().getId();
        try {
            System.out.println("进入的线程id"+id);
            Thread.sleep(1000 * 5);
        } catch (Exception e) {
            System.out.println("出错了！");
        } finally {
            System.out.println("线程"+id+"释放了锁");
            lock.unlock();

        }
        return "hello";
    }


    @RequestMapping("/setCountDownLatch")
    @ResponseBody
    public String setCountDownLatch(Integer count){
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("countDownLatch");
        countDownLatch.trySetCount(count);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "基数为0！";
    }


    @RequestMapping("/go")
    @ResponseBody
    public String go(String a){
        RCountDownLatch countDownLatch = redisson.getCountDownLatch("countDownLatch");
        countDownLatch.countDown();
        System.out.println(a+"离开了!");
        return a+"离开了";
    }


    /**
     * 车库停车 信号量测试
     */
    @RequestMapping("/park")
    @ResponseBody
    public String park() throws InterruptedException {
        RSemaphore park = redisson.getSemaphore("park");
        park.acquire();
        return "ok";
    }

    @RequestMapping("/go1")
    @ResponseBody
    public String go1(){
        RSemaphore park = redisson.getSemaphore("park");
        park.release();
        return "go";
    }




    @RequestMapping("/write")
    @ResponseBody
    public String writeValue(){
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock wLock = readWriteLock.writeLock();
        long id = Thread.currentThread().getId();
        wLock.lock();
        System.err.println(id+":写锁加锁成功！");
        try{
            String uuid = UUID.randomUUID().toString();
            redisCache.setCacheObject("writeValue",uuid);
            Thread.sleep(1000*5);

        }catch (Exception e){
            System.err.println("程序出错！");
        }finally {
            System.err.println(id+":写锁释放成功！。。。。。。。。。。。。。。。。");
            wLock.unlock();
        }
        return "ok";
    }


    @RequestMapping("/read")
    @ResponseBody
    public String readValue(){
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = readWriteLock.readLock();
        long id = Thread.currentThread().getId();
        rLock.lock();
        System.out.println(id+":读锁加锁成功！");
        String cacheObject = "";
        try {
             cacheObject = redisCache.getCacheObject("writeValue");

        }catch (Exception e){
            System.out.println("程序出错！");
        }finally {
            System.out.println(id+":读锁释放成功！。。。。。。。。。。。。。。。。");
            rLock.unlock();
        }

        return cacheObject;


    }





}
