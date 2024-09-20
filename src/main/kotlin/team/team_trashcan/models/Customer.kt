package team.team_trashcan.models

import kotlinx.serialization.Serializable
import team.team_trashcan.data.ServiceLevel

/**
 * Represents a customer in the system with an ID, name, service level, and notes.
 *
 * @property id The unique identifier for the customer.
 * @property name The name of the customer.
 * @property serviceLevel The service level of the customer, which defaults to Bronze.
 * @property notes Any additional notes about the customer.
 */
@Serializable
data class Customer (
    val id: Int,
    val name: String,
    val serviceLevel: ServiceLevel = ServiceLevel.Bronze,
    val notes: String = "",
)