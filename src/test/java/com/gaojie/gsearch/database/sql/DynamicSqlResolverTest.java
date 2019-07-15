package com.gaojie.gsearch.database.sql;

import java.util.List;

import org.dom4j.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gaojie.gsearch.database.sql.template.DynamicSqlTemplete;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicSqlResolverTest {

	@Autowired
	private DynamicSqlResolver dynamicSqlResolver;

	@Test
	public void test1() {
		try {
			List<DynamicSqlTemplete> tempList = dynamicSqlResolver.resolve();
			for (int i = 0; i < tempList.size(); i++) {
				DynamicSqlTemplete temp = tempList.get(i);
				System.out.println("======第" + i + "行参数======");
				System.out.println("id:" + temp.getId());
				System.out.println("sql:" + temp.getSql());
				System.out.println("parase sql:" + temp.getSql().replaceAll("(\r\n|\r|\n|\n\r)", ""));
				System.out.println("cron:" + temp.getCron());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

}
