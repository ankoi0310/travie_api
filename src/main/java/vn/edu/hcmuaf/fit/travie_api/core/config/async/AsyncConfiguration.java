package vn.edu.hcmuaf.fit.travie_api.core.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration {
    @Bean(name = "threadPoolTaskExecutorForVerificationMail")
    public ThreadPoolTaskExecutor threadPoolTaskExecutorForVerificationMail() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("threadPoolTaskExecutorForVerificationMail-");
        executor.initialize();
        return executor;
    }
}
