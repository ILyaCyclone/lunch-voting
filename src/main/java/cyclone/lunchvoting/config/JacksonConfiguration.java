package cyclone.lunchvoting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {
//    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // http://lewandowski.io/2016/02/formatting-java-time-with-spring-boot-using-json/
        // I hope there is an easier way to set global time format
//        javaTimeModule.addSerializer(LocalTime.class, new JsonSerializer<LocalTime>() {
//            @Override
//            public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//                jsonGenerator.writeString(localTime.format(TIME_FORMATTER));
//            }
//        });
//        javaTimeModule.addDeserializer(LocalTime.class, new JsonDeserializer<LocalTime>() {
//            @Override
//            public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//                return LocalTime.parse(jsonParser.getValueAsString(), TIME_FORMATTER);
//            }
//        });

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


        objectMapper.registerModule(javaTimeModule);
        objectMapper.registerModule(new Hibernate5Module()); // don't serialize lazy associations
        return objectMapper;
    }
}
