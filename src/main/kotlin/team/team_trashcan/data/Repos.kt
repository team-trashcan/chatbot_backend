package team.team_trashcan.data

import io.ktor.server.plugins.NotFoundException
import team.team_trashcan.models.Customer
import team.team_trashcan.models.Employee
import team.team_trashcan.models.Ticket

/**
 * Singleton object that hosts various repository objects for managing
 * Customers, Tickets, and Employees.
 */
object Repos {

    /**
     * Repository for managing Customers.
     */
    object Customers : Repository<Customer> {
        override suspend fun getAll(): List<Customer> = suspendTransaction {
            CustomerDAO.all().map { it.toModel() }
        }

        override suspend fun get(id: Int): Customer? = suspendTransaction {
            CustomerDAO
                .findById(id)
                ?.toModel()
        }

        override suspend fun add(entity: Customer) = suspendTransaction {
            CustomerDAO.new {
                name = entity.name
                serviceLevel = entity.serviceLevel
                notes = entity.notes
            }
        }.toModel()

        override suspend fun delete(id: Int): Boolean = suspendTransaction {
            CustomerDAO.findById(id)?.delete() != null
        }
    }

    /**
     * Repository for managing Customers.
     */
    object Tickets : Repository<Ticket> {
        override suspend fun getAll(): List<Ticket> = suspendTransaction {
            TicketDAO.all().map { it.toModel() }
        }

        override suspend fun get(id: Int): Ticket? = suspendTransaction {
            TicketDAO
                .findById(id)
                ?.toModel()
        }

        override suspend fun add(entity: Ticket) = suspendTransaction {
            TicketDAO.new {
                title = entity.title
                customer = CustomerDAO.findById(entity.customer.id)
                    ?: throw NotFoundException("No customer with id ${entity.customer.id}")
                issueDescription = entity.issueDescription
                employee = EmployeeDAO.findById(entity.employee.id)
                    ?: throw NotFoundException("No employee with id ${entity.employee.id}")
                express = entity.express
                serviceLevel = entity.serviceLevel
            }
        }.toModel()

        override suspend fun delete(id: Int): Boolean = suspendTransaction {
            TicketDAO.findById(id)?.delete() != null
        }
    }


    /**
     * Repository for managing Customers.
     */
    object Employees : Repository<Employee> {
        override suspend fun getAll(): List<Employee> = suspendTransaction {
            EmployeeDAO.all().map { it.toModel() }
        }

        override suspend fun get(id: Int): Employee? = suspendTransaction {
            EmployeeDAO
                .findById(id)
                ?.toModel()
        }

        override suspend fun add(entity: Employee) = suspendTransaction {
            EmployeeDAO.new {
                firstName = entity.firstName
                lastName = entity.lastName
                specialties = entity.specialties
            }
        }.toModel()

        override suspend fun delete(id: Int): Boolean = suspendTransaction {
            EmployeeDAO.findById(id)?.delete() != null
        }
    }
}