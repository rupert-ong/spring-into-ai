package dev.danvega.simple;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

  // In the most simple use case, Spring AI provides Spring Boot autoconfiguration, creating a prototype ChatClient.Builder
  // bean for you to inject into your class.

  // You can disable the ChatClient.Builder autoconfiguration by setting the property spring.ai.chat.client.enabled=false.
  // This is useful if multiple chat models are used together. Then, create a ChatClient.Builder instance programmatically
  // for every ChatModel you need:

  // ```java
  // ChatModel myChatModel = ... // usually autowired
  // ChatClient.Builder builder = ChatClient.builder(this.myChatModel);
  //
  // // or create a ChatClient with the default builder settings:
  // ChatClient chatClient = ChatClient.create(this.myChatModel);
  // ```

  private final ChatClient chatClient;

  public ChatController(ChatClient.Builder builder) {
    this.chatClient = builder
        // Can define how the system should respond to the user
        .defaultSystem("You are a loud assistant that responds in all capital letters.")
        .build();
  }

  @GetMapping("/")
  public String chat(@RequestParam(value = "message", defaultValue = "Tell me a dad joke about dogs") String message) {
    return chatClient.prompt()
        .user(message) // sets the contents of the user message
        .call() // sends a request to the AI model
        .content(); // returns response as string. Short for getResult().getOutput().getContent();
  }

  @GetMapping("/jokes")
  public String jokes(@RequestParam(value = "topic", defaultValue = "cats") String topic) {
    return chatClient.prompt()
        .user(user -> user.text("Tell me a dad joke about {topic}").param("topic", topic))
        .call()
        .content();
  }
}
