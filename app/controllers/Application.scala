package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import models.{SignInForm, User}
import play.api.data.Forms._
import play.api.db.DB
import anorm._
import models.SignInForm
import models.SignInForm

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

  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("Invalid email or password", result => result match {
      case (email, password) => UserController.authenticate(email, password).isDefined
    })
  )

  def index = Action {
    Ok(views.html.index("Welcome to Play-Scala App", signInForm, signUpForm))
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => Redirect(routes.Application.index), //BadRequest(views.html.index(formWithErrors)),
      user => Redirect(routes.UserController.list).withSession("email" -> user._1)
    )
  }

}