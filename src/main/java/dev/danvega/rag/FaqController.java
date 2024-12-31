package dev.danvega.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faq")
public class FaqController {
  private final ChatClient chatClient;

  public FaqController(ChatClient.Builder builder, VectorStore vectorStore) {
    this.chatClient = builder
        .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
        .build();
  }

  // http :8080/faq message=="How can I buy tickets for the Olympic Games Paris 2024"
  @GetMapping
  public String faq(@RequestParam(value = "message", defaultValue = "How can I buy tickets for the Olympic Games Paris 2024")
                    String message) {
    return chatClient.prompt()
        .user(message)
        .call()
        .content();
  }
}
