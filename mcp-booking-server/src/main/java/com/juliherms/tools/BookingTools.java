package com.juliherms.tools;

import com.juliherms.model.Booking;
import com.juliherms.model.CategoryEnum;
import com.juliherms.service.BookingService;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

/**
 * BookingTools class provides methods to interact with the BookingService.
 * It allows users to retrieve booking details, cancel bookings, and list available travel packages by category.
 */
@ApplicationScoped
public class BookingTools {

    @Inject
    BookingService bookingService;

    /**
     * Retrieves the complete details of a booking based on its unique identifier (bookingId).
     *
     * @param bookingId The unique numeric ID of the booking (e.g., 12345).
     * @return A string representation of the booking details or a message indicating that the booking was not found.
     */
    @Tool(name = "getBookingDetails", description = "Obtém os detalhes completos de uma reserva com base em seu número de identificação (bookingId).")
    public String getBookingDetails(
            @ToolArg(description = "O ID numérico único da reserva (ex: 12345)") long bookingId) {
        return bookingService.getBookingDetails(bookingId)
                .map(Booking::toString)
                .orElse("Reserva com ID " + bookingId + " não encontrada.");
    }

    /**
     * Cancels an existing booking based on its unique identifier (bookingId).
     * The user must be authenticated to perform this action.
     *
     * @param bookingId The unique numeric ID of the booking to cancel.
     * @param name      The name of the user attempting to cancel the booking.
     * @return A message indicating whether the cancellation was successful or not.
     */
    @Tool(name = "cancelBooking", description = """
                Cancela uma reserva existente com base no seu ID (bookingId).
                O usuário deve estar autenticado.
            """)
    public String cancelBooking(
            @ToolArg(description = "ID da reserva a cancelar") long bookingId,
            @ToolArg(description = "Usuário que está tentando cancelar a reserva") String name) {
        return bookingService.cancelBooking(bookingId, name)
                .map(b -> "Reserva " + b.id() + " cancelada com sucesso.")
                .orElse("Não foi possível cancelar a reserva. Verifique se o ID está correto e se você tem permissão.");
    }

    /**
     * Lists all available travel packages for a specified category (e.g., ADVENTURE, TREASURES).
     *
     * @param category The category used as a filter for travel packages.
     * @return A string representation of the available packages or a message indicating that no packages were found.
     */
    @Tool(name = "listPackagesByCategory", description = "Lista os pacotes de viagem disponíveis para uma determinada categoria (ex: ADVENTURE, TREASURES).")
    public String listPackagesByCategory(
            @ToolArg(description = "Categoria utilizada como filtro para pacotes") CategoryEnum category) {
        List<Booking> packages = bookingService.findPackagesByCategory(category);
        if (packages.isEmpty()) {
            return "Nenhum pacote encontrado para a categoria: " + category;
        }
        return "Pacotes encontrados para a categoria '" + category + "': " + packages.stream()
                .map(Booking::destination)
                .toList().toString();
    }
}
