package dev.danvega.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/youtube")
public class YouTubeController {
  private final ChatClient chatClient;

  @Value("classpath:/prompts/youtube.st")
  private Resource ytPromptResource;

  public YouTubeController(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  @GetMapping("/popular")
  public String findPopularYoutubersHardcoded(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
    String message = """
        List 10 of the most popular YouTubers in {genre} along with their current subscriber count. If you don't know
        the answer, just say "I don't know".
        """;
    return chatClient.prompt()
        .user(user -> user.text(message).param("genre", genre))
        .call()
        .content();
  }

  // This method is the same as the previous one, but the prompt is loaded from a file.
  @GetMapping("/popular-resource")
  public String findPopularYouTubers(@RequestParam(value = "genre", defaultValue = "tech") String genre) {
    return chatClient.prompt()
        .user(user -> user.text(ytPromptResource).param("genre", genre))
        .call()
        .content();
  }
}
