package com.juliherms.security;

import com.juliherms.ai.ToneJudge;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Classe responsável por validar o tom das respostas do modelo de IA.
 * Verifica se a resposta gerada pelo modelo é profissional e adequada para um agente de viagens sênior.
 * Se a resposta for considerada rude ou informal, solicita ao modelo que reescreva mantendo a polidez e formalidade.
 */
@ApplicationScoped
public class ToneGuardRail implements OutputGuardrail {

    @Inject
    ToneJudge judge; // Injeção do serviço ToneJudge para avaliar o tom das respostas

    public OutputGuardrailResult validate(AiMessage aiMessage) {
        if (!judge.isProfessional(aiMessage.text())) {
            return reprompt(aiMessage.text(), """
                            Sua resposta foi detectada como rude ou informal demais.
                            "Reescreva-a mantendo a polidez e formalidade de um agente de viagens sênior.
            """);
        }
        return OutputGuardrailResult.success();
    }
}
