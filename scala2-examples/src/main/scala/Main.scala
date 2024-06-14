import java.time.ZonedDateTime

import scala.util.Random

import io.scalaland.chimney.auto
import io.scalaland.chimney.dsl
import io.scalaland.chimney.dsl._
import io.scalaland.chimney.inlined
import io.scalaland.chimney.internal
import io.scalaland.chimney.partial
import io.scalaland.chimney.partial._
import io.scalaland.chimney.syntax
import io.scalaland.chimney.PartialTransformer
import io.scalaland.chimney.Patcher
import io.scalaland.chimney.Transformer

object Main extends App {

  println("Hello, World!")
  case class MakeCoffee(id: Int, kind: String, addict: String)
  case class CoffeeMade(id: Int, kind: String, forAddict: String, at: ZonedDateTime)

  val command: MakeCoffee = MakeCoffee(id = Random.nextInt, kind = "Espresso", addict = "Piotr")

  val event: CoffeeMade = command
    .into[CoffeeMade]
    .withFieldComputed(_.at, _ => ZonedDateTime.now)
    .withFieldRenamed(_.addict, _.forAddict)
    .transform
  // CoffeeMade(24, "Espresso", "Piotr", "2020-02-03T20:26:59.659647+07:00[Europe/Warsaw]")

  case class UserForm(name: String, ageInput: String, email: Option[String])
  case class User(name: String, age: Int, email: String)

  UserForm("John", "21", Some("john@example.com"))
    .intoPartial[User]
    .withFieldComputedPartial(_.age, form => Result.fromCatching(form.ageInput.toInt))
    .transform
    .asOption // Some(User("name", 21, "john@example.com"))

  val result: Result[User] = UserForm("Ted", "eighteen", None)
    .intoPartial[User]
    .withFieldComputedPartial(_.age, form => Result.fromCatching(form.ageInput.toInt))
    .transform

  result.asOption // None
  // result.asErrorMessageStrings
  // Iterable("age" -> "For input string: \"eighteen\"", "email" -> "empty value")

}
