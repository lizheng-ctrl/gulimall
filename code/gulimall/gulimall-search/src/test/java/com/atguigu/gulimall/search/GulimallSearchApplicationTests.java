package com.atguigu.gulimall.search;

import com.alibaba.fastjson.JSON;
import com.atguigu.gulimall.search.config.GulimallElasticSearchConfig;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.AvgAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@SpringBootTest
@RunWith(SpringRunner.class)
class GulimallSearchApplicationTests {


	@Autowired
	private RestHighLevelClient client;
	@Test
	public void contextLoads() {
		System.out.println(client);
	}


	/**测试存储数据*/
	@Test
	public void indexData() throws IOException {
		IndexRequest indexRequest = new IndexRequest("users");
		indexRequest.id("1");//数据的id
		//indexRequest.source("useName","zhangSan","age",18,"gender","男");
		User user = new User();
		user.setAge(12);
		user.setUserName("张三");
		user.setGender("男");
		String s = JSON.toJSONString(user); //要保存的数据
		indexRequest.source(s, XContentType.JSON);
		//执行操作
		IndexResponse index = client.index(indexRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
		System.out.println(index);
	}

	/**
	 *
	 * @author lizheng
	 * @date 2022/11/27 23:02
	 * @return void
	 */
	@Test
	public void searchData() throws IOException {
		//1. 创建检索请求
		SearchRequest searchRequest = new SearchRequest();
		// 指定索引
		searchRequest.indices("bank");
		// 指定DSL 检索条件
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 构造检索条件
		searchSourceBuilder.query(QueryBuilders.matchQuery("address","mill"));

		//1.2 按照年龄的值分布聚合
		TermsAggregationBuilder ageAgg = AggregationBuilders.terms("ageAgg").field("age").size(10);
		searchSourceBuilder.aggregation(ageAgg);
		//1.3 计算平均薪资
		AvgAggregationBuilder balanceAvg = AggregationBuilders.avg("balanceAvg").field("balance");
		searchSourceBuilder.aggregation(balanceAvg);



		searchRequest.source(searchSourceBuilder);
		System.out.println("检索条件"+searchSourceBuilder.toString());
		//执行检索
		SearchResponse searchResponse = client.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

		//分析结果
		System.out.println(searchResponse.toString());
		//解析对象
		Map map = JSON.parseObject(searchResponse.toString(), Map.class);

		//3.1 获取所有查到的数据
		SearchHits hits = searchResponse.getHits();
		for (SearchHit hit : hits) {
			String sourceAsString = hit.getSourceAsString();
			Account account = JSON.parseObject(sourceAsString, Account.class);
			System.out.println("account"+account);
		}

		//3.2 获取聚合
		Aggregations aggregations = searchResponse.getAggregations();
		Terms ageAgg1 = aggregations.get("ageAgg");
		List<? extends Terms.Bucket> buckets = ageAgg1.getBuckets();
		for (Terms.Bucket bucket : buckets) {
			String keyAsString = bucket.getKeyAsString();
			System.out.println("年龄"+keyAsString+"===>"+bucket.getDocCount());
		}


	}






	@Data
	class User{
		private String userName;
		private String gender;
		private Integer age;
	}


	@Data
	@ToString
	public static class Account {
		private int account_number;
		private int balance;
		private String firstname;
		private String lastname;
		private int age;
		private String gender;
		private String address;
		private String employer;
		private String email;
		private String city;
		private String state;
	}


}
