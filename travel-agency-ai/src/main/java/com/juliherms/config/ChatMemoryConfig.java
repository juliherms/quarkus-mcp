package com.juliherms.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Configuração do ChatMemory para armazenar o histórico de mensagens.
 * Mantém as últimas 20 mensagens no histórico.
 */
@ApplicationScoped
public class ChatMemoryConfig {

    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .maxMessages(20) // Mantêm as últimas 20 mensagens no histórico
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .build();
    }
}
