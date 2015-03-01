package controllers

import play.api._
import play.api.mvc._

import views._
import models._
import play.api.data._
import play.api.data.Forms._
import play.api.db.DB
import anorm._
import play.api.Play.current

object LoginController extends Controller {

  val signInForm: Form[User] = Form(
    mapping(
      "firstName" -> text,
      "lastName" -> text,
      "email" -> email,
      "phone" -> text,
      "company" -> text,
      "username" -> text,
      "password" -> text
    )(User.apply)(User.unapply)
  )

  def form = Action {
    Ok(html.login.form(signInForm))
  }

  def signin = Action {

    implicit request =>
      signInForm.bindFromRequest.fold(
        errors => BadRequest(html.login.form(errors)),
        user => {
          DB.withConnection {
            implicit c =>
              val resultSet = SQL(
                """
                  SELECT * FROM User
                """
              )
              println("Here: " + resultSet)
          }
          Ok(html.home.home(user))
        }
      )
    //println("Hello World")
  }

}
