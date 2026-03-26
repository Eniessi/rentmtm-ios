package com.rentmtm.model

import com.rentmtm.model.enums.BudgetStatus
import kotlinx.serialization.Serializable

@Serializable
data class Budget(
    var id: Long = 0L,

    // Rastreabilidade: Quem pede e quem presta
    var customerId: Long,      // ID do User que está contratando
    var professionalId: Long,  // ID do User (Profissional) que vai prestar o serviço

    // O "Coração" do Orçamento: Explicação do serviço
    var serviceTitle: String? = null,       // Ex: "Reparo de fiação elétrica"
    var serviceDescription: String? = null, // Explicação detalhada do problema/necessidade

    // Detalhes Financeiros e Prazos
    var estimatedValue: Double = 0.0,
    var requestDate: String? = null,        // Data da solicitação
    var scheduledDate: String? = null,      // Data combinada para o serviço

    // Localização (Onde o "Uber de serviço" vai atuar)
    // Usamos Composição aqui para reutilizar o nosso model de Address
    var serviceLocation: Address? = null,

    // Estado da Negociação
    var status: BudgetStatus = BudgetStatus.PENDING,
    var additionalNotes: String? = null,    // Observações extras (ex: "levar escada alta")

    // Referência ao pagamento (se houver)
    var paymentMethodId: Long? = null
)