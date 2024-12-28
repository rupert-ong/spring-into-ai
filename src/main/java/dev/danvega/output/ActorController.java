package dev.danvega.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActorController {
  private final ChatClient chatClient;

  public ActorController(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  // Gets data as string (seen before)
  @GetMapping("/films-string")
  public String getActorFilmsString() {
    return chatClient.prompt()
        .user("Generate a filmography for Anthony Hopkins for the year 2010")
        .call()
        .content();
  }

  // Gets structured data
  @GetMapping("/films")
  public ActorFilms getActorFilms() {
  return chatClient.prompt()
      .user("Generate a filmography for the actor Anthony Hopkins")
      .call()
      .entity(ActorFilms.class);
  }

  // Get list of structured data
  @GetMapping("/films-list")
  public List<ActorFilms> listActorFilms() {
    return chatClient.prompt()
        .user("Generate a filmograpy for the actors Denzel Washington, Leonardo DiCaprio and Tom Hanks")
        .call()
        .entity(new ParameterizedTypeReference<>() {});
  }
}
