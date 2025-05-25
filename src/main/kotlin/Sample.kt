package org.example

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.agent.config.AIAgentConfig
import ai.koog.agents.core.dsl.builder.forwardTo
import ai.koog.agents.core.dsl.builder.strategy
import ai.koog.agents.core.dsl.extension.nodeLLMRequest
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.local.features.eventHandler.feature.handleEvents
import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.llms.MultiLLMPromptExecutor
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.message.Message
import ai.koog.prompt.structure.json.JsonSchemaGenerator
import ai.koog.prompt.structure.json.JsonStructuredData
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File


fun main(): Unit = runBlocking {
    val token = System.getenv("OPENAI_API_KEY") ?: throw IllegalArgumentException("OPENAI_API_KEY env is not set")

    val articleStructure = JsonStructuredData.createJsonStructure<Article>(
        schemaFormat = JsonSchemaGenerator.SchemaFormat.JsonSchema,
        examples = articleExamples,
        schemaType = JsonStructuredData.JsonSchemaType.SIMPLE
    )

    val extractionAgentStrategy = strategy("statement-extraction") {
        val setup by nodeLLMRequest()

        val extractStatements by node<Message.Response, String> { _ ->
            val structuredResponse = llm.writeSession {
                this.requestLLMStructured(
                    structure = articleStructure,
                    fixingModel = OpenAIModels.CostOptimized.GPT4_1Nano
                ).getOrThrow()
            }
            val article = structuredResponse.structure
            val jsonString = Json.encodeToString(serializer<Article>(), value = article)
            val file = File("samples/${article.publisher}.json")
            file.writeText(jsonString)
            println("dumped result to ${file.absolutePath}")
            """
                Response structure:
                ${structuredResponse.structure}
                """.trimIndent()
        }

        edge(nodeStart forwardTo setup)
        edge(setup forwardTo extractStatements)
        edge(extractStatements forwardTo nodeFinish)
    }

    val extractionAgentConfig = AIAgentConfig(
        prompt = prompt("statement-extraction") {
            system(
                """
                You are a the first stage in the fact checking pipeline.
                When asked to analyze a news article, you must identify the Facts, Judgements and Quotes in it, according to the definitions below:
                
                - Fact is a statement that is true and can be confirmed by evidence provided by a known source or derived with logical reasoning.
                - Value Judgement is a statement that displays the opinion or estimation held by a person or organization.
                - Quote is a direct citation of statement by some specific person or organization.
                
                The statement might have an implied external source and you must try to identify it.
                In case there is no external source, you must set the source to the article author.
                You must very carefully distinguish the facts from value judgements for further processing.
            """.trimIndent()
            )
        },
        model = OpenAIModels.CostOptimized.GPT4_1Nano,
        maxAgentIterations = 5
    )

    val comparisonAgentStrategy = strategy("statement-comparison") {
        val nodeSendInput by nodeLLMRequest()
        val save by node<Message.Response, String> { it ->
            val file = File("samples/result.txt")
            file.writeText(it.toString())
            "dumped result to ${file.absolutePath}"
        }

        edge(nodeStart forwardTo nodeSendInput)
        edge(nodeSendInput forwardTo save)
        edge(save forwardTo nodeFinish)
    }

    val comparisonAgentConfig = AIAgentConfig(
        prompt = prompt("statement-comparison") {
            system(
                """
                You are a the second stage in the fact checking pipeline.
                You will be provided with multiple pre-processed articles on the same topic, but from different sources, with statements sorted into three categories:
                1. Fact - a statement that is true and can be confirmed by evidence provided by a known source or derived with logical reasoning.
                2. Value Judgement - a statement that displays the opinion or estimation held by a person or organization.
                3. Quote - a direct citation of statement by some specific person or organization.
                
                You must carefully compare the statements of each category between the two articles and distinguish where they match and where they differ.
            """.trimIndent()
            )
        },
        model = OpenAIModels.CostOptimized.GPT4_1Nano,
        maxAgentIterations = 5
    )

    val extractionAgent = AIAgent(
        promptExecutor = MultiLLMPromptExecutor(
            LLMProvider.OpenAI to OpenAILLMClient(token),
        ),
        strategy = extractionAgentStrategy, // no tools needed for this example
        agentConfig = extractionAgentConfig,
        toolRegistry = ToolRegistry.EMPTY
    ) {
        handleEvents {
            onAgentRunError = { strategyName: String, throwable: Throwable ->
                println("An error occurred: ${throwable.message}\n${throwable.stackTraceToString()}")
            }

            onAgentFinished = { strategyName: String, result: String? ->
                println("Result: $result")
            }
        }
    }

    extractionAgent.run(apArticleVisas)
    println("Finished analyzing the AP article.")
    extractionAgent.run(foxArticleVisas)
    println("Finished analyzing the Fox article.")
    extractionAgent.run(cnnArticleVisas)
    println("Finished analyzing the CNN article.")

    val comparisonAgent = AIAgent(
        promptExecutor = MultiLLMPromptExecutor(
            LLMProvider.OpenAI to OpenAILLMClient(token),
        ),
        strategy = comparisonAgentStrategy, // no tools needed for this example
        agentConfig = comparisonAgentConfig,
        toolRegistry = ToolRegistry.EMPTY
    ) {
        handleEvents {
            onAgentRunError = { strategyName: String, throwable: Throwable ->
                println("An error occurred: ${throwable.message}\n${throwable.stackTraceToString()}")
            }

            onAgentFinished = { strategyName: String, result: String? ->
                println("Result: $result")
            }
        }
    }

    val articles = File("samples")
        .walk()
        .filter { it.isFile }
        .map { it.readText(Charsets.UTF_8) }
        .toList()
//        println(articles.toString())
    comparisonAgent.run(articles.toString())
}