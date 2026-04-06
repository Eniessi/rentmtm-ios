import com.rentmtm.model.Budget

// IBudgetRepository.kt
interface IBudgetRepository {
    // Cliente: Cria o pedido inicial
    suspend fun createClientRequest(request: Budget): Long

    // Profissional: Busca os pedidos abertos
    suspend fun getPendingRequestsForProfessional(): List<Budget>

    // Qualquer um: Busca um orçamento específico
    suspend fun getBudgetById(id: Long): Budget?

    // Profissional: Envia o valor do orçamento
    suspend fun submitProfessionalQuote(id: Long, quoteValue: Double, notes: String)

    // Cliente: Aceita o orçamento (Gera a Ordem de Serviço)
    suspend fun acceptQuoteAndCreateOrder(budgetId: Long): Long
}