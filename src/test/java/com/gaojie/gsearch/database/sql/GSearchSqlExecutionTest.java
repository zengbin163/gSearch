package com.gaojie.gsearch.database.sql;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gaojie.gsearch.database.sql.template.DynamicSqlTemplete;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GSearchSqlExecutionTest {

	@Autowired
	private DynamicSqlResolver dynamicSqlResolver;
	@Autowired
	private GSearchSqlExecution gSearchSqlExecution;

	@Test
	public void test1() {
		try {
			List<DynamicSqlTemplete> tempList = dynamicSqlResolver.resolve();
			for (int i = 0; i < tempList.size(); i++) {
				DynamicSqlTemplete temp = tempList.get(i);
				System.out.println("======第" + (i+1) + "条sql执行======");
				System.out.println("sql:" + temp.getSql());
				this.gSearchSqlExecution.execute(temp.getSql());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
