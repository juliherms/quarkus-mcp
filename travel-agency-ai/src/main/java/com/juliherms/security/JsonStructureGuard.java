package com.juliherms.security;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.StringReader;

/**
 * Classe responsável por validar a estrutura JSON das respostas do modelo de IA.
 * Verifica se a resposta gerada pelo modelo é um JSON válido.
 * Se a resposta não for um JSON válido, solicita ao modelo que gere novamente apenas o JSON,
 * sem blocos de código markdown ou texto adicional.
 * Esta estratégia tem custo adicional de tokens, pois envolve uma nova chamada ao modelo para corrigir a resposta.
 */
@ApplicationScoped
public class JsonStructureGuard implements OutputGuardrail {

    public OutputGuardrailResult validate(AiMessage aiMessage) {
        String response = aiMessage.text();
        try (JsonReader reader = Json.createReader(new StringReader(response))) {
            // Tenta ler a resposta como um objeto JSON
            JsonObject jsonObject = reader.readObject();
            return OutputGuardrailResult.success();
        } catch (Exception e) {
            // Se ocorrer uma exceção, significa que a resposta não é um JSON válido
            //Ensinamos o modelo a gerar apenas o JSON, sem blocos de código markdown ou texto adicional.
            return reprompt(aiMessage.text(), """
                    Erro: Sua resposta não é um JSON válido.
                    Problema encontrado: " + e.getMessage() " . " +
                    Gere NOVAMENTE apenas o JSON, sem blocos de código markdonw ou texto adicional.
                    """);
        }
    }
}
