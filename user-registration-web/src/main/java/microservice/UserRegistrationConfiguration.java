package microservice;

import microservice.dustview.DustViewResolver;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class UserRegistrationConfiguration {

	@Bean
	public DustViewResolver dustViewResolver(){
		DustViewResolver resolver = new DustViewResolver();
		resolver.setPrefix("/dustjsviews/");
		resolver.setSuffix(".dust");
		return resolver;
	}

	@Bean
	public ObjectMapper jacksonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		for (HttpMessageConverter<?> httpMessageConverter : restTemplate.getMessageConverters()) {
			if(httpMessageConverter instanceof MappingJackson2HttpMessageConverter){
				((MappingJackson2HttpMessageConverter)httpMessageConverter).setObjectMapper(jacksonObjectMapper());
			}
		}

		return restTemplate;
	}
	
}
