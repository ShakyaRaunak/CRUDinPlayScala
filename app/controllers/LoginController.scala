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

  val loginForm: Form[SecUser] = Form(
    mapping(
      //"firstName" -> text,
      //"lastName" -> text,
      //"company" -> text,
      //"email" -> email,
      "username" -> text,
      "password" -> text
    )(SecUser.apply)(SecUser.unapply)
  )

  def form = Action {
    Ok(html.login.form(loginForm))
  }

  def login = Action {
    implicit request =>
      loginForm.bindFromRequest.fold(
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
  }

}
