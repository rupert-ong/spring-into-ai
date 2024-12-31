# Spring AI

Topics covered in this repository:

- Basic Spring AI API setup and usage
- Streaming responses
- Prompt Engineering and Message Roles (with String Templates)
- Structured Output conversion

### References

- [Spring AI Docs](https://docs.spring.io/spring-ai/reference/index.html)

## Getting Started

Be sure to obtain an API key from OpenAI and set it in your environment variables as `OPENAI_API_KEY`.

## Prompt Engineering

Prompts serve as the foundation for language-based inputs that guide an AI model to produce specific outputs. In Spring,
prompt management relies on Text Template Engines, where there is static and dynamic text.

### Roles

There are different roles in the prompt engineering process:

- **System**: Guides the AI's behaviour and response style, setting rules for how the AI interprets and replies to
  input.
- **User**: Represents the user's inputs to the AI.
- **Assistant**: The AI's response to the user's input, which is also crucial to maintaining the flow of the
  conversation. By tracking the AI's previous responses (it's 'Assistant Role' messages), the system ensures coherent
  and contextually relevant interactions.
- **Function**: Deals with specific tasks or operations during the conversation, such as performing calculations,
  fetching data or other tasks beyond just talking.

#### References

- [OpenAI Guidelines](https://platform.openai.com/docs/guides/prompt-engineering)
- [ChatGPT Prompt Engineering for Developers](https://www.deeplearning.ai/short-courses/chatgpt-prompt-engineering-for-developers/)

## Structured Output

The Spring AI Structured Output Converters help to convert the LLM output into a structured format. As shown in the
following diagram, this approach operates around the LLM text completion endpoint:

![Structured Output Architecture Diagram](./assets/images/structured-output-architecture.jpg)

Before the LLM call, the converter appends format instructions to the prompt, providing explicit guidance to the models
on generating the desired output structure. These instructions act as a blueprint, shaping the model’s response to
conform to the specified format.

After the LLM call, the converter takes the model’s output text and transforms it into instances of the structured type.
This conversion process involves parsing the raw text output and mapping it to the corresponding structured data
representation, such as JSON, XML, or domain-specific data structures.

Implemented converters include:

- Bean Output Converter
- Map Output Converter
- List Output Converter

Both a (simplified) high level and low level API are provided for structured output conversion.

### References

- [Spring AI Structured Output](https://docs.spring.io/spring-ai/reference/api/structured-output-converter.html)

## Using Additional Data in AI Models

There are several strategies to incorporate additional data into AI models, as they may only be trained on public
knowledge up until a certain date or do not know about private or corporate data.

- Fine Tune the Model: Train the model on your data (most difficult)
- Stuffing the Prompt: Adding your data (context) into the prompt -> adds to token cost
- Function Calling: LLM calls function to retrieve data
- RAG (Retrieval Augmented Generation): Use a retrieval model to fetch **relevant** data

### RAG

Retrieval Augmented Generation (RAG) has emerged to address the challenge of incorporating relevant data into prompts
for accurate AI model responses. At a high level, this is an ETL (Extract, Transform and Load) pipeline. The vector
database is used in the retrieval part of RAG technique.

![ETL Pipeline](./assets/images/spring-ai-rag.jpg)

The job reads unstructured data from your documents, transforms it and then writes it into a vector database, which
is queried based on similarity (rather than exact match as in the tradition database query).

Part of loading the unstructured data into the vector database involves splitting the original document into smaller
pieces. Care must be taken not to split the documents in the middle of sentences, paragraphs, code methods, etc.

#### References

- [RAG Concept](https://docs.spring.io/spring-ai/reference/concepts.html#concept-rag)
- [RAG Advisor API](https://docs.spring.io/spring-ai/reference/api/retrieval-augmented-generation.html)
- [Vector Databases](https://docs.spring.io/spring-ai/reference/api/vectordbs.html)

## Resources

### Httpie Installation

Install `httpie` using the following commands:

```bash
python -m pip install --upgrade pip wheel
python -m pip install httpie
```

To add it to your system environment variables path:

```bash
# Verify installation
python -m pip show httpie
# Show user base
python -m site --user-base
```

Copy `user base` value from above into your file explorer. Append to this value the location of the executables.
In my case the total value was `C:\Users\<username>\AppData\Roaming\Python\Python310\Scripts`

#### References

- [Installation Guide](https://httpie.io/docs/cli/installation)
- [Usage Guide](https://httpie.io/docs/cli/examples)
