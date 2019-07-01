package com.gaojie.gsearch.database.sql;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gaojie.gsearch.base.IndexerParam;
import com.gaojie.gsearch.index.Indexer;
import com.gaojie.gsearch.service.GSearchSqlService;

/**
 * 搜索引擎构建索引的sql执行引擎
 * 
 * @author zengbin
 *
 */
@Configuration
public class GSearchSqlExecution {

	@Autowired
	private GSearchSqlService gSearchSqlService;
	@Autowired
	private Indexer indexer;
	
	private static final Logger logger = LoggerFactory.getLogger(GSearchSqlExecution.class);

	public void execute(String sql) throws Exception {
		if (StringUtils.isBlank(sql)) {
			throw new IllegalArgumentException("执行sql为空");
		}
		Long total = this.gSearchSqlService.countResultByPage(sql);
		Long totalPages = total % IndexerParam.SELECT_PAGE_SIZE == 0 ? total / IndexerParam.SELECT_PAGE_SIZE : total / IndexerParam.SELECT_PAGE_SIZE + 1;
		Long indexId = 1L;
		for (Long currentPage = 1L; currentPage <= totalPages; currentPage++) {
			IPage<Map<String, Object>> pages = this.gSearchSqlService.getResultByPage(sql, currentPage, IndexerParam.SELECT_PAGE_SIZE);
			List<Map<String, Object>> listMap = pages.getRecords();
			indexer.build(IndexerParam.INDEX_SEARCH_INDEXER, indexId.toString(), listMap);
			++indexId;
		}
		logger.info("=========构建索引执行总数 = {} ==========", total);
	}

}
