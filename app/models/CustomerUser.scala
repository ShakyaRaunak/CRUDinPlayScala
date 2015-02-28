package models

case class CustomerUser(
    firstName: String,
    lastName: String,
    company: String,
    email: String,
    secUser: SecUser
)

case class SecUser(
                    username: String,
                    password: String

                    )
