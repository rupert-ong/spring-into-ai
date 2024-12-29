package dev.danvega.stuff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

// Stuffing the prompt demo
@RestController
@RequestMapping("/olympics")
public class OlympicsController {
  private static final Logger log = LoggerFactory.getLogger(OlympicsController.class);

  private final ChatClient chatClient;

  @Value("classpath:/prompts/olympic-sports.st")
  private Resource olympicSportsPromptResource;

  @Value("classpath:/docs/olympic-sports.txt")
  private Resource docsToStuffResource;

  public OlympicsController(ChatClient.Builder builder) {
    this.chatClient = builder.build();
  }

  // http :8080/olympics/2024 stuffIt==true
  @GetMapping("/2024")
  public String get2024OlympicSports(
      @RequestParam(value = "message", defaultValue = "What sports are being included in the 2024 Summer Olympics?")
      String message,
      @RequestParam(value = "stuffIt", defaultValue = "false") boolean stuffIt
  ) throws IOException {
    String sports = docsToStuffResource.getContentAsString(Charset.defaultCharset());
    log.info("Sports: {}", sports);

    return chatClient.prompt()
        .user(u -> u.text(olympicSportsPromptResource)
            .param("question", message)
            .param("context", stuffIt ? sports : ""))
        .call()
        .content();
  }
}
