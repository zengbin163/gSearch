package com.gaojie.gsearch.database.job;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * 动态定时任务执行引擎
 * 
 * @author zengbin
 *
 */
@Component
@EnableScheduling
public class DynamicSqlExecutionTask implements SchedulingConfigurer {
	/*****这个不能删除，必填*****/
	private String cron = "* * 0/5 * * ?"; //首次构建索引的时间，这个也就是系统启动后5秒开始构建索引，后续执行频率以传入的表达式为准
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				// 任务逻辑
				System.out.println("------------------------DynamicExecutionTask 开始执行构建索引--------" + System.currentTimeMillis());
				System.out.println("开始执行构建索引，时间表达式cron为：" + cron); //后续这里增加构建索引的逻辑
				try {
					Thread.sleep(10000L);
				}catch (Exception e) {
					// TODO: handle exception
				}
				System.out.println("------------------------DynamicExecutionTask 结束执行构建索引--------" + System.currentTimeMillis());
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				CronTrigger cronTrigger = new CronTrigger(cron);
				Date nextExecDate = cronTrigger.nextExecutionTime(triggerContext);
				return nextExecDate;
			}
		});
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
}
