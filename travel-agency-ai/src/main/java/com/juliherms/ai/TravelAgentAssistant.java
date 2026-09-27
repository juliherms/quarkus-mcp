package com.juliherms.ai;

import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * Interface que define o serviço de assistente virtual para agentes de viagem.
 * Este serviço é responsável por interagir com os clientes, respondendo suas perguntas
 * relacionadas a viagens e pacotes turísticos.
 */
@RegisterAiService
public interface TravelAgentAssistant {

    String chat(String userMessage);

}
