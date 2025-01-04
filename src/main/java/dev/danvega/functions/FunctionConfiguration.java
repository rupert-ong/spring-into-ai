package dev.danvega.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class FunctionConfiguration {
  private final WeatherConfigProperties weatherConfigProperties;

  public FunctionConfiguration(WeatherConfigProperties weatherConfigProperties) {
    this.weatherConfigProperties = weatherConfigProperties;
  }

  // Register the function (as a bean)
  // The description helps the model to understand when to call the function.
  @Bean
  @Description("Get the current weather conditions for a given city.")
  public Function<WeatherService.Request, WeatherService.Response> currentWeatherFunction() {
    return new WeatherService(weatherConfigProperties);
  }
}
