package dev.danvega.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class RagConfiguration {

  private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

  @Value("classpath:/docs/olympic-faq.txt")
  private Resource faq;

  @Value("vectorstore.json")
  private String vectorStoreName;

  @Bean
  SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
    SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);
    File vectorStoreFile = getVectorStoreFile();

    if (vectorStoreFile.exists()) {
      log.info("Vector store file exists: {}", vectorStoreFile);
      simpleVectorStore.load(vectorStoreFile);
    } else {
      log.info("Vector store files does not exist, loading documents");
      TextReader textReader = new TextReader(faq);
      textReader.getCustomMetadata().put("filename", "olympic-faq.txt");
      List<Document> documents = textReader.get();
      TokenTextSplitter textSplitter = new TokenTextSplitter();
      List<Document> splitDocuments = textSplitter.apply(documents);

      simpleVectorStore.add(splitDocuments);
      simpleVectorStore.save(vectorStoreFile);
    }

    return simpleVectorStore;
  }

  // For purposes of this example, we are creating/using a (vectorstore.json) JSON file to store the vectors
  private File getVectorStoreFile() {
    var path = Paths.get("src", "main", "resources", "data");
    var absolutePath = path.toFile().getAbsoluteFile() + "/" + vectorStoreName;
    return new File(absolutePath);
  }
}
