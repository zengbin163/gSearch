package com.gaojie.gsearch.index;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @desc
 * @author 曾斌
 * @version 创建时间：Jun 30, 2019 12:31:59 AM
 */
@Component
public class Indexer {

	@Autowired
	private RestHighLevelClient client;
	private static final Logger logger = LoggerFactory.getLogger(Indexer.class);

	/**
	 * 构建索引
	 * 
	 * @throws IOException
	 */
	public void build(String index, String id, List<Map<String, Object>> list) throws IOException {
		if (StringUtils.isBlank(index)) {
			throw new IllegalArgumentException("Index is null");
		}
		if (null == id) {
			throw new IllegalArgumentException("Id is null");
		}
		if (CollectionUtils.isEmpty(list)) {
			throw new IllegalArgumentException("list is null");
		}
		IndexRequest request = new IndexRequest(index);
		Long indexId = 1L;
		for (Map<String, Object> map : list) {			
			String docId = id + indexId;
			++indexId;
			logger.info("the build index doc Id = {}", docId);
			request.id(docId).opType("create").source(map,XContentType.JSON);
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			logger.info("response.status = {}", response.status());
		}
	}

	/**
	 * 更新索引
	 * 
	 * @throws IOException
	 */
	public void update(String index, String id, Map<String, Object> map) throws IOException {
		if (StringUtils.isBlank(index)) {
			throw new IllegalArgumentException("Index is null");
		}
		if (null == id) {
			throw new IllegalArgumentException("Id is null");
		}
		if (null == map || map.size() == 0) {
			throw new IllegalArgumentException("map is null");
		}
		UpdateRequest request = new UpdateRequest(index, id);
		request.doc(map);
		UpdateResponse response = this.client.update(request, RequestOptions.DEFAULT);
		logger.info("response.status = {}", response.status());
	}

	/**
	 * 删除索引
	 * 
	 * @param index
	 * @param id
	 * @throws IOException
	 */
	public void delete(String index, String id) throws IOException {
		if (StringUtils.isBlank(index)) {
			throw new IllegalArgumentException("Index is null");
		}
		if (null == id) {
			throw new IllegalArgumentException("Id is null");
		}
		DeleteRequest request = new DeleteRequest(index, id);
		DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
		logger.info("response.status = {}", response.status());
	}

}