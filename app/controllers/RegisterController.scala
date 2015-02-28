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

object RegisterController extends Controller {

  val registerForm: Form[CustomerUser] = Form(
    mapping(
      "firstName" -> text,
      "lastName" -> text,
      "company" -> text,
      "email" -> email,
      "secUser" -> mapping(
        "username" -> nonEmptyText,
        "password" -> text(minLength = 6)
      )(SecUser.apply)(SecUser.unapply)

    )(CustomerUser.apply)(CustomerUser.unapply)
  )

  def form = Action {
    Ok(html.register.form(registerForm))
  }

  def register = Action {
    implicit request =>
      registerForm.bindFromRequest.fold(
        errors => BadRequest(html.register.form(errors)),
        user => {
          DB.withConnection {
            implicit c =>
            //val registerQuery = SQL("insert into User(firstName, lastName, company, email, username, password) values ({firstName}, {lastName}, {company}, {email}, {username}, {password})").on("Raunak", "Shakya", "rks", "rkshakya99gmail.com", 'raunakshakya', 'raunakshakya').execute

              SQL(
                """
                  INSERT INTO SecUser(username, password) VALUES ({username}, {password})
                """).on(
                'username -> secUser.username,
                'password -> secUser.password
              ).executeUpdate()//,

              /*SQL(
                """
                  INSERT INTO User(firstName, lastName, company, email, secUser) VALUES ({firstName}, {lastName}, {company}, {email}, {secUser})
                """).on(
                'firstName -> user.firstName,
                'lastName -> user.lastName,
                'company -> user.company,
                'email -> user.email,
                'secUser -> secUser
              ).executeUpdate()*/
          }
          Ok(html.register.summary(secUser))
        }
      )
  }

}
