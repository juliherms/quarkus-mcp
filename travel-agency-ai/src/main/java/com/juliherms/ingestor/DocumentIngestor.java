package com.juliherms.ingestor;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.arc.profile.UnlessBuildProfile;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * Classe responsável por ingerir documentos e armazenar seus embeddings.
 * <p>
 * Esta classe é observadora do evento de inicialização da aplicação (StartupEvent).
 * Ao iniciar a aplicação, ela carrega um documento específico, divide-o em segmentos
 * e armazena os embeddings desses segmentos em uma loja de embeddings.
 */
@ApplicationScoped
@UnlessBuildProfile("test")
public class DocumentIngestor {

    private static final String RAG_DOCUMENT_PATH = "/rag/pacotes-viagem.md";

    @Inject
    EmbeddingStore<TextSegment> store;

    @Inject
    EmbeddingModel embeddingModel;

    /**
     * Método que é chamado quando a aplicação é iniciada.
     * Ele carrega um documento, define seu tipo, divide-o em segmentos e armazena os embeddings.
     *
     * @param event Evento de inicialização da aplicação
     */
    public void onStart(@Observes StartupEvent event) {
        Document document = loadFromClasspath(RAG_DOCUMENT_PATH);

        document.metadata().put("type", "packages");

        // Define o splitter para dividir o documento em segmentos menores
        DocumentSplitter splitter = DocumentSplitters.recursive(200, 20);

        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(store)
                .build();

        ingestor.ingest(document);
    }

    private Document loadFromClasspath(String classpathLocation) {
        try (InputStream inputStream = getClass().getResourceAsStream(classpathLocation)) {
            if (inputStream == null) {
                throw new IllegalStateException("Recurso não encontrado no classpath: " + classpathLocation);
            }
            return new TextDocumentParser().parse(inputStream);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
