package com.gaojie.gsearch.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaojie.gsearch.base.IndexerParam;
import com.gaojie.gsearch.search.Searcher;

/**
 * 搜索组件
 * @author zengbin
 * @since 2019-06-30 18:39
 */
@RestController
@RequestMapping("/searcher")
public class SearcherController {

	@Autowired
	private Searcher searcher;

	@GetMapping("/search")
	public Map<String, Object> search(@RequestParam("id") String id) throws IOException {
		return this.searcher.search(IndexerParam.INDEX_SEARCH_INDEXER, id);
	}

	@PostMapping("/index")
	public List<Map<String, Object>> query(@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "firstCategoryName", required = false) String firstCategoryName,
			@RequestParam(value = "secondCategoryName", required = false) String secondCategoryName,
			@RequestParam(value = "thirdCategoryName", required = false) String thirdCategoryName,
			@RequestParam(value = "brandName", required = false) String brandName,
			@RequestParam(value = "pageStart", required = true) Integer pageStart,
			@RequestParam(value = "pageSize", required = true) Integer pageSize) throws IOException {
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
		return this.searcher.searchList(IndexerParam.INDEX_SEARCH_INDEXER, pageStart, pageSize, paramMap);
	}
}
