package com.juliherms.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;

/**
 * Configuração do Retrieval Augmentor para recuperação de informações.
 * Este componente é responsável por configurar o mecanismo de recuperação de conteúdo
 * baseado em embeddings armazenados.
 */
@ApplicationScoped
public class RagConfiguration {

    @Produces
    public RetrievalAugmentor retrievalAugmentor(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel) {

        // Configura o Retrieval Augmentor com um Content Retriever baseado em Embedding Store
        return DefaultRetrievalAugmentor.builder()
                .contentRetriever(EmbeddingStoreContentRetriever.builder()
                        .embeddingStore(embeddingStore)
                        .embeddingModel(embeddingModel)
                        .maxResults(5) // Define o número máximo de resultados a serem retornados
                        .build())
                .build();
    }
}
