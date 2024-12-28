package dev.danvega.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/treasure")
public class TreasureController {
  private final ChatClient chatClient;

  public TreasureController(ChatClient.Builder builder) {
    this.chatClient = builder
        // System role in prompt
        .defaultSystem("Please respond to any question in the voice of a pirate")
        .build();
  }

  @GetMapping
  public String treasureFacts() {
    return chatClient.prompt()
        // User role in prompt
        .user("Tell me a really interesting fact about famous pirate treasure. Please keep your answer to 1 to 2 sentences")
        .call()
        .content();
  }
}
