package lambdas.utils

import java.time.temporal.ChronoUnit
import java.time.LocalDate

object DateUtils {

  def getDaysFrom(startDate: String): Long = {
    val start: LocalDate = LocalDate.parse(startDate)
    val end: LocalDate = LocalDate.now()
    ChronoUnit.DAYS.between(start, end)
  }

}
