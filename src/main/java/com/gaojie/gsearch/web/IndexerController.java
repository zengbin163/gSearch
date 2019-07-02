package com.gaojie.gsearch.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaojie.gsearch.base.IndexerParam;
import com.gaojie.gsearch.database.sql.DynamicSqlResolver;
import com.gaojie.gsearch.database.sql.GSearchSqlExecution;
import com.gaojie.gsearch.database.sql.template.DynamicSqlTemplete;
import com.gaojie.gsearch.index.Indexer;

/**
 * 
 * 构建索引indexer
 * @author zengbin
 * @since 2019-06-30 18:39
 */
@RestController
@RequestMapping("/indexer")
public class IndexerController {

	@Autowired
	private Indexer indexer;
	@Autowired
	private DynamicSqlResolver dynamicSqlResolver;
	@Autowired
	private GSearchSqlExecution gSearchSqlExecution;

	private static final Logger logger = LoggerFactory.getLogger(IndexerController.class);

	@PostMapping("/create")
	public Map<String, Object> create(@RequestParam(value = "sqlId", required = false) Integer sqlId) throws Exception {
		List<DynamicSqlTemplete> tempList = dynamicSqlResolver.resolve();
		Map<String, Object> map = new HashMap<>();
		for (DynamicSqlTemplete temp : tempList) {
			logger.warn("the build index doc sql id={}", temp.getId());
			map.put("sqlId", temp.getSql());
			if (null == sqlId) {
				this.gSearchSqlExecution.execute(temp.getSql()); //如果sqlId不传入就构建全文索引
			} else {
				if (sqlId.equals(temp.getId())) {
					this.gSearchSqlExecution.execute(temp.getSql()); //如果sqlId传入，就构建指定的sqlId索引
				}
			}
		}
		return map;
	}

	@PostMapping("/update")
	public String update(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "firstCategoryName", required = false) String firstCategoryName,
			@RequestParam(value = "secondCategoryName", required = false) String secondCategoryName,
			@RequestParam(value = "thirdCategoryName", required = false) String thirdCategoryName,
			@RequestParam(value = "brandName", required = false) String brandName) throws IOException {
		Map<String, Object> paramMap = new HashMap<>();
		if (StringUtils.isNotBlank(productName)) {
			paramMap.put("productName", productName);
		}
		if (StringUtils.isNotBlank(firstCategoryName)) {
			paramMap.put("firstCategoryName", firstCategoryName);
		}
		if (StringUtils.isNotBlank(secondCategoryName)) {
			paramMap.put("secondCategoryName", secondCategoryName);
		}
		if (StringUtils.isNotBlank(thirdCategoryName)) {
			paramMap.put("thirdCategoryName", thirdCategoryName);
		}
		if (StringUtils.isNotBlank(brandName)) {
			paramMap.put("brandName", brandName);
		}
		this.indexer.update(IndexerParam.INDEX_SEARCH_INDEXER, id, paramMap);
		return "success";
	}

	@DeleteMapping("/delete")
	public String deleteOrder(@RequestParam(value = "id", required = true) String id) throws IOException {
		this.indexer.delete(IndexerParam.INDEX_SEARCH_INDEXER, id);
		return "success";
	}
}
