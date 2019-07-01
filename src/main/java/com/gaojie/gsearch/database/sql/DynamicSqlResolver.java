package com.gaojie.gsearch.database.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.gaojie.gsearch.database.sql.template.DynamicSqlTemplete;

/**
 * 动态sql处理模板，根据用户配置的sql语句查询数据
 * 
 * @author zengbin
 *
 */
@Configuration
public class DynamicSqlResolver {

	@Value("${dynamic.sql.path}")
	private String dynamicSqlFile;

	/**
	 * 解析动态sql
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public List<DynamicSqlTemplete> resolve() throws DocumentException {
		// 创建saxReader对象
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(dynamicSqlFile));
		// 获取根节点元素对象
		Element node = document.getRootElement();
		// 遍历所有的元素节点
		return this.resolveNodes(node);
	}

	/**
	 * 遍历当前节点元素下面的所有(元素的)子节点
	 * 
	 * @param node
	 */
	public List<DynamicSqlTemplete> resolveNodes(Element node) {
		if (null == node) {
			throw new IllegalArgumentException("node参数值为空");
		}
		List<DynamicSqlTemplete> tempList = new ArrayList<DynamicSqlTemplete>();
		// 当前节点下面子节点迭代器
		Iterator<Element> it = node.elementIterator();
		// 遍历
        List<Integer> ids = new ArrayList<>();
		while (it.hasNext()) {
			DynamicSqlTemplete temp = new DynamicSqlTemplete();
			// 获取某个子节点对象
			Element e = it.next();
			if ("nodeMapping".equals(e.getName())) {
			    if(null==e.element("id") || StringUtils.isBlank(e.element("id").getStringValue())) {
			        throw new IllegalArgumentException("DynamicSqlTemplate \'id\' is null");
                } else {
                    Integer id = Integer.valueOf(this.replaceX(e.element("id").getStringValue()));
                    if(ids.contains(id)) {
                        throw new IllegalArgumentException("DynamicSqlTemplate \'id\' = " + id + " is repeated");
                    }
                    ids.add(id);
                    temp.setId(id);
                }
			    
			    if(null==e.element("field") || StringUtils.isBlank(e.element("field").getStringValue())) {
                    throw new IllegalArgumentException("DynamicSqlTemplate \'field\' is null");
                } else {
                    temp.setField(this.replaceX(e.element("field").getStringValue()));
                }
			    
                if (null == e.element("sql") || StringUtils.isBlank(e.element("sql").getStringValue())) {
                    throw new IllegalArgumentException("DynamicSqlTemplate \'sql\' is null");
                } else {
                    temp.setSql(this.replaceX(e.element("sql").getStringValue()));
                }

                if (null == e.element("cron") || StringUtils.isBlank(e.element("cron").getStringValue())) {
                    throw new IllegalArgumentException("DynamicSqlTemplate \'cron\' is null");
                } else {
                    temp.setCron(this.replaceX(e.element("cron").getStringValue()));
                }
				tempList.add(temp);
			}
			// 对子节点进行遍历
			this.resolveNodes(e);
		}
		return tempList;
	}
	
	/**
	 * 替换非法字符
	 * @param element
	 * @return
	 */
	private String replaceX(String element) {
	    if(StringUtils.isBlank(element)) {
	        return element;
	    }
	    return element.replaceAll("(\r\n|\r|\n|\n\r|\t)", " ").trim();
	}
}
