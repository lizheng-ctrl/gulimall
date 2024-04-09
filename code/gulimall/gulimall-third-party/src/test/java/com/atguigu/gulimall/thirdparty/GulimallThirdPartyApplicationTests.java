package com.atguigu.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import com.atguigu.gulimall.thirdparty.component.SmsComponent;
import com.atguigu.gulimall.thirdparty.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class GulimallThirdPartyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	OSSClient ossClient;

	@Autowired
	SmsComponent smsComponent;

    @Test
	public void testUpload() throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream("C:\\Users\\lizheng\\Desktop\\1ad5ad6eddc451da566bf5d5c7e79661d0163215.jpeg");
		ossClient.putObject("lizheng-gulimall","guimeizhiren.jpg", fileInputStream);
		ossClient.shutdown();
		System.out.println("上传成功");
	}





	@Test
	public void sendMessage(){
		smsComponent.sendSmsCode("13856465053","5678");
	}


}
