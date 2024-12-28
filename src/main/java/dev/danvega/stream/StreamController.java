package dev.danvega.stream;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamController {
  private final ChatClient chatClient;

  public StreamController(ChatClient.Builder chatClient) {
    this.chatClient = chatClient.build();
  }

  // http --stream :8080/stream
  // Streams response, non blocking
  @GetMapping("/stream")
  public Flux<String> stream(@RequestParam(
      value = "message",
      defaultValue = "Give me the top 10 barbeque places to eat at in Kansas") String message) {
    return chatClient.prompt()
        .user(message)
        // non blocking
        .stream()
        .content();
  }
}
