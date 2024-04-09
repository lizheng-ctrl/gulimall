package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.thirdparty.component.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
public class SmsSendController {

    /**
     * 提供给别的服务进行调用的
     * @param phone
     * @param code
     * @return
     */
    @Autowired
    SmsComponent smsComponent;

    @GetMapping("/sendCode")
    public R sendCode(@RequestParam("phone") String phone,
                      @RequestParam("code") String code){

        smsComponent.sendSmsCode(phone,code);
        return R.ok();
    }

}
