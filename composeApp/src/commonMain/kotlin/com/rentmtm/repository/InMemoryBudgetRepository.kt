import com.rentmtm.model.Budget
import com.rentmtm.model.enums.BudgetStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

// InMemoryBudgetRepository.kt
class InMemoryBudgetRepository : IBudgetRepository {

    // Nossa "Tabela" do banco de dados em memória
    private val budgetTable = MutableStateFlow<List<Budget>>(emptyList())
    private var autoIncrementId = 1L

    override suspend fun createClientRequest(request: Budget): Long {
        delay(500) // Simula latência de rede
        val newId = autoIncrementId++
        val newRequest = request.copy(
            id = newId,
            status = BudgetStatus.PENDING // Status inicial
        )
        budgetTable.value = budgetTable.value + newRequest
        return newId
    }

    override suspend fun getPendingRequestsForProfessional(): List<Budget> {
        delay(500)
        return budgetTable.value.filter { it.status == BudgetStatus.PENDING }
    }

    override suspend fun getBudgetById(id: Long): Budget? {
        delay(300)
        return budgetTable.value.find { it.id == id }
    }

    override suspend fun submitProfessionalQuote(id: Long, quoteValue: Double, notes: String) {
        delay(800)
        budgetTable.update { currentList ->
            currentList.map { budget ->
                if (budget.id == id) {
                    budget.copy(
                        estimatedValue = quoteValue,
                        additionalNotes = notes,
                        status = BudgetStatus.QUOTED // Mudança de estado crucial!
                    )
                } else budget
            }
        }
    }

    override suspend fun acceptQuoteAndCreateOrder(budgetId: Long): Long {
        delay(1000)
        budgetTable.update { currentList ->
            currentList.map { budget ->
                if (budget.id == budgetId) budget.copy(status = BudgetStatus.ACCEPTED) else budget
            }
        }
        // Aqui simularia a criação na tabela de ServiceOrder e retornaria o OrderId
        return budgetId + 10000L
    }
}