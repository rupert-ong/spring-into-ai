package dev.danvega.functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {
  private final ChatClient chatClient;

  public CityController(ChatClient.Builder builder) {
    this.chatClient = builder
        .defaultSystem("You are a helpful AI assistant answering questions about cities around the world.")
        // Assign the currentWeatherFunction bean to the chat model
        .defaultFunctions("currentWeatherFunction")
        .build();
  }

  // http :8080/cities message=="What is the current weather like in Columbus?"
  @GetMapping("/cities")
  public String cityFaq(@RequestParam(value = "message") String message) {
    return chatClient.prompt().user(message).call().content();
  }
}
