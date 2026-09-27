package com.juliherms.resource;

import com.juliherms.ai.PackageExpertWithTemplate;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 * Recurso REST para o agente de viagem.
 * Este recurso expõe um endpoint que permite aos usuários fazer perguntas relacionadas a viagens.
 * Ele utiliza o serviço PackageExpertWithTemplate para processar as perguntas e fornecer respostas.
 */
@Path("/travel")
public class TravelAgentResource {

    @Inject
    PackageExpertWithTemplate expert;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String ask(String question, @HeaderParam("X-User-Name") String userName) {
        if (userName != null && !userName.isEmpty()) {
            return expert.chat(userName, question, userName);
        } else {
            return "Usuário precisa estar autenticado!";
        }
    }
}
