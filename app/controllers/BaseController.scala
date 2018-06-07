package controllers

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.mvc._


abstract class BaseController(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val formats = DefaultFormats
  def HttpOk[T](res : T) ={
    Ok(write(res))
  }
}
