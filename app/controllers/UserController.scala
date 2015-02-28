package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import anorm._
import play.api.Play.current

import views._
import models._
import play.api.db.DB

object UserController extends Controller {

  val registerForm: Form[User] = Form(
    mapping(
      "firstName" -> text,
      "lastName" -> text,
      "email" -> text,
      "phone" -> text,
      "company" -> text,
      "username" -> nonEmptyText,
      "password" -> text(minLength = 6)
    )(User.apply)(User.unapply)
  )

  def form = Action {
    Ok(html.register.form(registerForm))
  }

  /**
   * Create a new User instance
   */
  def createNewUser(user: User): User = {
    DB.withConnection {
      implicit connection =>
        SQL(
          """
            INSERT INTO User VALUES (
             {firstName}, {lastName}, {email}, {phone}, {company}, {username}, {password}
            )
          """
        ).on(
          'firstName -> user.firstName,
          'lastName -> user.lastName,
          'email -> user.email,
          'phone -> user.phone,
          'company -> user.company,
          'username -> user.username,
          'password -> user.password
        ).executeUpdate()

        user
    }
  }

  /**
   * Update the existing User instance
   */
  def updateNewUser(user: User): User = {
     user
  }

}