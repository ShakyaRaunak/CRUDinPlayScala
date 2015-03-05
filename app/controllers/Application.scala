package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import models.{SignInForm, User}
import play.api.data.Forms._

object Application extends Controller {

  val signUpForm: Form[User] = Form(
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

  val signInForm: Form[SignInForm] = Form(
    mapping(
      "email" -> email,
      "password" -> text
    )(SignInForm.apply)(SignInForm.unapply)
  )

  def index = Action {
    Ok(views.html.index("Welcome to Play-Scala App", signInForm, signUpForm))
  }

}