package org.example

import ai.jetbrains.code.prompt.executor.clients.grazie.koog.createGraziePromptExecutor
import ai.jetbrains.code.prompt.executor.clients.grazie.koog.model.GrazieEnvironment
import ai.jetbrains.code.prompt.llm.JetBrainsAIModels
import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.agent.config.AIAgentConfig
import ai.koog.agents.core.dsl.builder.strategy
import ai.koog.agents.core.dsl.extension.nodeLLMRequest
import ai.koog.agents.features.eventHandler.feature.handleEvents
import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.message.Message
import ai.koog.prompt.structure.json.JsonSchemaGenerator
import ai.koog.prompt.structure.json.JsonStructuredData
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

fun main(): Unit = runBlocking {
    val thinkingModel = JetBrainsAIModels.Anthropic_Haiku_3_5
    val fixingModel = JetBrainsAIModels.Google_FlashLite2_0

    val token = System.getenv("GRAZIE_TOKEN") ?: throw IllegalArgumentException("GRAZIE_TOKEN env is not set")
    val executor = createGraziePromptExecutor(token = token, grazieEnvironment = GrazieEnvironment.Staging)

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
                    structure = articleStructure, fixingModel = fixingModel
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
        nodeStart then setup then extractStatements then nodeFinish
    }

    val extractionPrompt = File("src/main/resources/gemini_prompt.md").readText().trimIndent()
//    val extractionPrompt = File("src/main/resources/my_prompt.txt").readText().trimIndent()

    val extractionAgentConfig = AIAgentConfig(
        prompt = prompt("statement-extraction") { system(extractionPrompt) },
        model = thinkingModel,
        maxAgentIterations = 5
    )

    val comparisonAgentStrategy = strategy("statement-comparison") {
        val nodeSendInput by nodeLLMRequest()
        val save by node<Message.Response, String> { it ->
            val file = File("samples/result.md")
            file.writeText(it.content.toString())
            "dumped result to ${file.absolutePath}"
        }
        nodeStart then nodeSendInput then save then nodeFinish
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
        }, model = thinkingModel, maxAgentIterations = 5
    )

    val extractionAgent = AIAgent(
        promptExecutor = executor,
        strategy = extractionAgentStrategy,
        agentConfig = extractionAgentConfig,
    ) {
        handleEvents {
            onToolCall { eventContext ->
                println("Tool called: ${eventContext.tool} with args ${eventContext.toolArgs}")
            }

            onAgentRunError { eventContext ->
                println("Agent error: ${eventContext.throwable.message}\n${eventContext.throwable.stackTraceToString()}")
            }

            onAgentFinished { eventContext ->
                println("Agent finished with result: ${eventContext.result}")
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
        promptExecutor = executor,
        strategy = comparisonAgentStrategy,
        agentConfig = comparisonAgentConfig,
    ) {
        handleEvents {
            onToolCall { eventContext ->
                println("Tool called: ${eventContext.tool} with args ${eventContext.toolArgs}")
            }

            onAgentRunError { eventContext ->
                println("Agent error: ${eventContext.throwable.message}\n${eventContext.throwable.stackTraceToString()}")
            }

            onAgentFinished { eventContext ->
                println("Agent finished with result: ${eventContext.result}")
            }
        }
    }

    val articles = File("samples").walk().filter { it.isFile }.map { it.readText(Charsets.UTF_8) }.toList()
//        println(articles.toString())
    comparisonAgent.run(articles.toString())
}