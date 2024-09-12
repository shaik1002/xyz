package hello;

import com.mongodb.MongoClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoDbSettings {

        @Bean
        public MongoClientSettings mongoOptions() {
            return MongoClientSettings.builder()
                    .applyToSocketSettings(builder -> {
                        builder.connectTimeout(2000, TimeUnit.MILLISECONDS)
                                .readTimeout(2000, TimeUnit.MILLISECONDS);
                    }).build();
        }

}
