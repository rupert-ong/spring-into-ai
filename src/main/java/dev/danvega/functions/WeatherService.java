package dev.danvega.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

/**
 * Weather API
 * <a href="https://www.weatherapi.com/api-explorer.aspx">See API documentation</a>
 */
public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {
  private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
  private final WeatherConfigProperties weatherConfigProperties;
  private final RestClient restClient;

  public WeatherService(WeatherConfigProperties weatherConfigProperties) {
    this.weatherConfigProperties = weatherConfigProperties;
    log.debug("Weather API URL: {}", weatherConfigProperties.apiUrl());
    log.debug("Weather API Key: {}", weatherConfigProperties.apiKey());
    this.restClient = RestClient.create(weatherConfigProperties.apiUrl());
  }

  @Override
  public Response apply(Request request) {
    log.info("Weather request: {}", request);
    Response response = restClient.get()
        .uri("/current.json?key={key}&q={q}", weatherConfigProperties.apiKey(), request.city())
        .retrieve()
        .body(Response.class);
    log.info("Weather response: {}", response);
    return response;
  }

  public record Request(String city) {}

  // Response Body as per API response
  public record Response(Location location, Current current) {}
  public record Location(String name, String region, String country, Long lat, Long lon) {}
  public record Current(String temp_f, Condition condition, String wind_mph, String humidity) {}
  public record Condition(String text) {}
}
