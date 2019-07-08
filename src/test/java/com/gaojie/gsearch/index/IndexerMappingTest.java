package com.gaojie.gsearch.index;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @desc
 * @author 曾斌
 * @version 创建时间：Jun 30, 2019 1:45:01 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexerMappingTest {

	@Autowired
	private RestHighLevelClient client;
	private static final Logger logger = LoggerFactory.getLogger(IndexerMappingTest.class);

	@Test
	public void build() throws IOException {

		this.createIndex();

	}

	public XContentBuilder createIndex() throws IOException {
		String index = "zengbintest";
		String docId = "1000";
		IndexRequest request = new IndexRequest(index);

		XContentBuilder mapping = null;
		mapping = XContentFactory.jsonBuilder();
		mapping.startObject();
		mapping.startObject("properties");
		for (int i = 0; i < 100; i++) {
			mapping.startObject("title1").field("type", "string").field("title", "张三").endObject();
			mapping.startObject("question1").field("type", "string").field("question", "服装")
					.field("analyzer", "ik_max_word").endObject();
			mapping.startObject("answer").field("type", "text").field("answer", "西装").field("analyzer", "ik_max_word")
					.endObject();
			mapping.startObject("category").field("type", "text").field("category", "服装西装")
					.field("analyzer", "ik_max_word").endObject();
			mapping.startObject("author").field("type", "string").field("index", "not_analyzed").endObject();
			mapping.startObject("date").field("type", "string").field("index", "not_analyzed").endObject();
			mapping.startObject("answer_author").field("type", "string").field("index", "not_analyzed").endObject();
			mapping.startObject("answer_date").field("type", "string").field("index", "not_analyzed").endObject();
			mapping.startObject("description").field("type", "string").field("index", "not_analyzed").endObject();
			mapping.startObject("keywords").field("type", "string").field("index", "not_analyzed").endObject();
			mapping.startObject("read_count").field("type", "integer").field("index", "not_analyzed").endObject();
		}
		// 关联数据
		// mapping.startObject("list").field("type", "object").endObject();
		mapping.endObject();
		mapping.endObject();
		request.id(docId).opType("create").source(mapping, XContentType.JSON);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		logger.info("response.status = {}", response.status());

		return mapping;
	}

	public void createIndex2() throws IOException {
		String index = "zengbintest";
		String docId = "1000";
		IndexRequest request = new IndexRequest(index);
		JSONObject list = new JSONObject();
		for (int i = 0; i < 10; i++) {
			JSONArray listMap = new JSONArray();
			JSONObject map = new JSONObject();
			map.put("type", "string");
			map.put("title", "张三" + i);
			listMap.add(map);

			map.put("type", "string");
			map.put("question", "服装" + i);
			map.put("analyzer", "ik_max_word");
			listMap.add(map);

			map.put("type", "string");
			map.put("answer", "西装" + i);
			map.put("analyzer", "ik_max_word");
			listMap.add(map);

			map.put("type", "string");
			map.put("category", "服装西装" + i);
			map.put("analyzer", "ik_max_word");
			listMap.add(map);

			map.put("type", "string");
			map.put("author", "曾斌" + i);
			map.put("index", "not_analyzed");
			listMap.add(map);

			list.put("key" + i, listMap);
		}
		// 关联数据
		// mapping.startObject("list").field("type", "object").endObject();
		String jsonData = new ObjectMapper().writeValueAsString(list);
		request.id(docId).opType("create").source(jsonData, XContentType.JSON);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		logger.info("response.status = {}", response.status());
	}

//	public void addIndexData() throws IOException {
//		IndexResponse response = client.prepareIndex("pages", "sina", null)
//				.setSource(jsonBuilder().startObject().field("article_title", "title".getBytes())
//						.field("article_content", "content".getBytes()).field("article_url", "url".getBytes())
//						.endObject())
//				.execute().actionGet();
//
//		client.close();
//
//	}

}
