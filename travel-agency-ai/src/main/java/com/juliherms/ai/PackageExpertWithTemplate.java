package com.juliherms.ai;

import com.juliherms.security.InjectionGuard;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.guardrail.InputGuardrails;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.mcp.runtime.McpToolBox;

/**
 * Interface que define o serviço de assistente virtual especializado em pacotes de viagem.
 * Este serviço é responsável por interagir com os clientes, respondendo suas perguntas
 * com base nas informações fornecidas nos documentos (RAG) ou utilizando ferramentas
 * disponíveis para interagir com o sistema de reservas.
 */
@RegisterAiService
public interface PackageExpertWithTemplate {

    @SystemMessage("""
        Você é um assistente virtual da 'Mundo Viagens', um especialista em nossos pacotes de viagem e reservas.
        Sua principal responsabilidade é responder às perguntas dos clientes de forma amigável e precisa,
        baseando-se exclusivamente nas informações contidas nos documentos que lhe foram fornecidos (RAG)
        ou utilizando as ferramentas disponíveis para interagir com o sistema de reservas.
        Nunca invente informações ou use conhecimento externo.
        Se a resposta para uma pergunta não estiver nos documentos e nenhuma ferramenta puder ajudar,
        você deve responder educadamente:
        'Desculpe, mas não tenho informações sobre isso. Posso ajudar com mais alguma dúvida sobre nossos pacotes?'
        """)
    @InputGuardrails(InjectionGuard.class) // Adiciona uma camada de segurança para validar a mensagem do usuário
    @McpToolBox("booking-server")
    // template de um prompt
    @UserMessage("Do what user is asking {message}. The user used for authentication is {username}.")
    String chat(@MemoryId String memoryId, String message, String username);
}
