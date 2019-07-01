package com.gaojie.gsearch.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaojie.gsearch.dao.GSearchSqlDao;

/**
 * @desc
 * @author 曾斌
 * @version 创建时间：Jun 29, 2019 1:04:07 PM
 */
@Service
public class GSearchSqlServiceImpl implements GSearchSqlService {

    @Autowired
    private GSearchSqlDao gSearchSqlDao;

    public Long countResultByPage(String sqlCount) {
        return this.gSearchSqlDao.executeCountDynamicSql(sqlCount);
    }

    @SuppressWarnings("unchecked")
    @Override
    public IPage<Map<String, Object>> getResultByPage(String sql, Long currentPage, Long pageSize) {
        if (currentPage < 1) {
            currentPage = 1L;
        }
        Long pageIndex = (currentPage - 1) * pageSize;
        List<Map<String, Object>> list = this.gSearchSqlDao.executeDynamicSql(sql, pageIndex, pageSize);
        IPage<Map<String, Object>> page = new Page();
        page.setCurrent(currentPage);
        page.setSize(pageSize);
        page.setRecords(list);
        return page;
    }
}
