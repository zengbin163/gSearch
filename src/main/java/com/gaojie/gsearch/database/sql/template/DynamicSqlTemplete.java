package com.gaojie.gsearch.database.sql.template;

import java.io.Serializable;

public class DynamicSqlTemplete implements Serializable {

    private static final long serialVersionUID = -8227943374226209435L;

    private Integer id; // 动态sql主键id
    private String cron; // 构建索引定时任务的执行时间
    private String field; // 需要构建的成document的字段值，跟下面sql里面的查询条件别名一一对应
    private String sql; // 需要构建索引的sql语句

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
