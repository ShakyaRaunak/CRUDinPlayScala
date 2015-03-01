package models

import anorm._
import anorm.SqlParser._

case class User(
                 firstName: String,
                 lastName: String,
                 email: String,
                 phone: String,
                 company: String,
                 username: String,
                 password: String
                 )

object User {

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[String]("user.firstName") ~
      get[String]("user.lastName") ~
      get[String]("user.email") ~
      get[String]("user.phone") ~
      get[String]("user.company") ~
      get[String]("user.username") ~
      get[String]("user.password") map {
      case firstName ~ lastName ~ email ~ phone ~ company ~ username ~ password => User(firstName, lastName, email, phone, company, username, password)
    }
  }

}