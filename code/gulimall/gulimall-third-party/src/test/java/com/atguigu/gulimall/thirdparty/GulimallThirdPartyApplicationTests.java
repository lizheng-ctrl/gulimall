package com.atguigu.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
class GulimallThirdPartyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	OSSClient ossClient;

    @Test
	public void testUpload() throws FileNotFoundException {
		FileInputStream fileInputStream = new FileInputStream("C:\\Users\\lizheng\\Desktop\\1ad5ad6eddc451da566bf5d5c7e79661d0163215.jpeg");
		ossClient.putObject("lizheng-gulimall","guimei.jpg", fileInputStream);
		ossClient.shutdown();
		System.out.println("上传成功");
	}


}
