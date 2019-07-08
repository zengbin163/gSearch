package com.gaojie.gsearch.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @desc
 * @author 曾斌
 * @version 创建时间：Jun 30, 2019 12:33:10 AM
 */
@Component
public class Searcher {

	@Autowired
	private RestHighLevelClient client;

	public Map<String, Object> search(String index, String id) throws IOException {
		if (StringUtils.isBlank(index)) {
			throw new IllegalArgumentException("Index is null");
		}
		if (null == id) {
			throw new IllegalArgumentException("Id is null");
		}
		GetRequest getRequest = new GetRequest(index, id);
		GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
		Map<String, Object> map = new HashMap<>();
		if (response.isExists()) {
			map.put("success", true);
			map.put("data", response.getSource());
		} else {
			map.put("success", false);
		}
		return map;
	}

	public List<Map<String, Object>> searchList(String index, Integer pageStart, Integer pageSize,
			Map<String, Object> paramMap) throws IOException {
		if (StringUtils.isBlank(index)) {
			throw new IllegalArgumentException("Index is null");
		}
		if (null == paramMap) {
			throw new IllegalArgumentException("paramMap is null");
		}
		
		SearchRequest request = new SearchRequest(index);
		// 构造bool查询
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
		}
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		// 排序
		//searchSourceBuilder.sort(SortBuilders.fieldSort(IndexerParam.SEARCH_SORTED_PARAM).order(SortOrder.DESC));
		// 分页
		searchSourceBuilder.from(pageStart).size(pageSize).query(boolQueryBuilder);
		//设置一个可选的超时，控制允许搜索的时间
		searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		request.searchType(SearchType.DEFAULT).source(searchSourceBuilder);
		List<Map<String, Object>> list = new ArrayList<>();
		for (SearchHit s : client.search(request, RequestOptions.DEFAULT).getHits().getHits()) {
			list.add(s.getSourceAsMap());
		}
		return list;
	}

}
