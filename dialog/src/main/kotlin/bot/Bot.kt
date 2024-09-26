package bot

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Bot {

    interface Model {
        fun generate(prompt: String): String
        fun generate(prompt: Prompt): String
    }

    class Prompt(
        outputPrompt: String,
        outputFormat: String,
        userMessage: String?
    )

    class Gpt4oMini : Model {
        override fun generate(prompt: String): String {
            TODO("Not yet implemented")
        }


        override fun generate(
            prompt: Prompt
        ): String {
            return generate(
                Json.Default.encodeToString(prompt)
            )
        }

    }

    private val model = Gpt4oMini()

    /**
     * Determines if the given message contains relevant information and returns an instance of Info
     * or null.
     *
     * @param message The message to check for relevant information.
     * @return An instance of Info if relevant information is found in the message, null otherwise.
     */
    fun extractInfo(message: String): Info? {
        // TODO: replace with command for the prompt as defined in `bot_config.yaml`: "/DetectInfo"
        // TODO: make sure the contents of bot_config.yaml are provided at the start of every chat to the bot
        return Json.Default.decodeFromString(
            model.generate(
                Prompt(
                    userMessage = message,
                    outputFormat = """
                        Format:
                        Do not generate a chat answer, even if the user is asking something.
                        Generate a JSON string to be deserialized into the following Kotlin class.
                        If you don't have information on a field, don't include it in the JSON array.
                        ```
                        @Serializable
                        data class Info(
                            var field: String,
                            var value: String,
                        )
                        ```
                        Details:
                        For [field] and for [value], use the following reference with the possible fields and their values:
                            TicketTitle: Short and concise title for the ticket, intended to be easily and quickly
                                understood by human technicians, but also by an AI when trying to use a list of
                                resolved issues to compare to a current one.
                                A title like 'Problem with [something]' is not allowed. Only generate a title as soon
                                as you have enough information for a concise title.
                                
                            TicketCategory: A category or topic for the issue to be easily and automatically
                                categorised in the ticket system and for easy assignment to technicians.
                            
                            TicketTags: A comma-separated list of tags, relating to the issue. The purpose is to know
                                which technologies are involved, to quickly determine which technician might be qualified,
                                before reading the ticket. This is also the place to include things that might be
                                relevant to the issue, like OS version, device, involved programs, etc.
                                
                            CustomerName: The name of the customer. Make sure it's not a joke or an online username.
                            
                            IssueExpectation: A detailed but concise description of what the user expected instead of
                                the issue they are experiencing. Do not generate a value for this field if the problem
                                has not been sufficiently described.
                                
                            IssueExperience: A detailed but concise description of what the user is experiencing
                                instead of what they were expecting. If possible, try to trace back what the user did
                                before, for the technicians to be able to recreate the issue.
                                 
                            Urgent: 'true' or 'false'. Put 'true' if the issue is hindering the customer's work
                                significantly and needs help ASAP. (Obviously, don't include the single quotes in the value.)
                    """.trimIndent(),
                    outputPrompt = """
                        Process the user message provided to you. Determine if it contains any concise information
                        relevant to the ticket fields.
                    """.trimIndent()
                )
            )
        )


    }

    /**
     * Generates the next message for the chatbot.
     *
     * @param prompt The prompt message to generate the next message.
     * @return The next message generated by the chatbot.
     */
    fun generateNextMessage(prompt: String): String {
        return model.generate(prompt)
    }

    /**
     * Generates a summary of the ticket based on the provided ticket information.
     *
     * @param ticket The ticket information represented as a MapTicket object.
     * @return A string containing the summary of the ticket.
     */
    fun generateTicketSummary(issue: Issue): String {
        TODO("Not yet implemented")
    }

    /**
     * Generates potential solutions for the given issue.
     * TODO: Keep old, resolved tickets and let tha bot use them to refer back to working solutions to similar problems
     * @return A string containing the potential solutions.
     */
    fun generatePotentialSolutions(): String {
        TODO("Not yet implemented")
    }

    companion object {
        /**
         * Creates a new session of the chatbot.
         *
         * @return A new instance of the Bot class.
         */
        fun newSession(): Bot {
            TODO("Not yet implemented")
        }
    }
}