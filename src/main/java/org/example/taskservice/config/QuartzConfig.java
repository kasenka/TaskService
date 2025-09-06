package org.example.taskservice.config;

import org.example.taskservice.component.TaskDeadlineJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail expiredTaskJobDetail() {
        return JobBuilder.newJob(TaskDeadlineJob.class)
                .withIdentity("expiredTaskJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger expiredTaskJobTrigger(JobDetail expiredTaskJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(expiredTaskJobDetail)
                .withIdentity("expiredTaskTrigger")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0 0 0 * * ?") // каждый день в 00:00
                                .withMisfireHandlingInstructionFireAndProceed() // если пропустили — выполнить при старте
                )
                .build();
    }
}
