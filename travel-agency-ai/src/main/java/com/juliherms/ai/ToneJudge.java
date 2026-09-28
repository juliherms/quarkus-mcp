package com.juliherms.ai;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

/**
 * Interface que define o serviço de auditor de qualidade para avaliar o tom das respostas.
 * Este serviço é responsável por analisar se uma resposta é profissional ou não,
 * com base em exemplos de reprovação e aprovação fornecidos.
 */
@RegisterAiService
public interface ToneJudge {

    @SystemMessage("""
            Você é um auditor de qualidade. Analise se a resposta é profissional.
            Exemplos de REPROVAÇÃO:
            - "Não é problema meu" -> Rude
            - "Se vira aí" -> Informal demais
            - "Cara, isso é chato" -> Gíria inadequada
            
            Exemplos de APROVAÇÃO:
            - "Sinto muito, mas isso está fora da minha alçada."
            - "Por favor, verifique os termos no site"
            
            Responda apenas 'true' se for profissional, ou 'false' se não for.
            """)
    boolean isProfessional(String text);
}
