package dev.danvega.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryChatController {
  private final ChatClient chatClient;

  public MemoryChatController(ChatClient.Builder builder) {
    this.chatClient = builder
        // chat memory advisor, using in-memory chat memory (no database or privacy issues)
        .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
        .build();
  }

  @GetMapping("/memory-chat")
  String chat(@RequestParam String message) {
    return chatClient.prompt()
        .user(message)
        .call()
        .content();
  }
}
