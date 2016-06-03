package persistence

import com.github.tototoshi.slick.GenericJodaSupport

import scala.slick.driver.JdbcDriver
import scala.slick.model.ForeignKeyAction

trait Profile {
  val driver: JdbcDriver
}

trait DBComponent {
  this: Profile =>

  object JodaSupport extends GenericJodaSupport(driver)

  lazy val onDeleteAction: ForeignKeyAction = ForeignKeyAction.Cascade
}
