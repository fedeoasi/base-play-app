package persistence

import org.joda.time.DateTime

object Daos {
  case class RatingDao(userId: Int, titleId: Int, ratingType: String, rating: Double, modifiedTime: DateTime, id: Option[Int] = None)
  case class VideoDao(titleId: Int, userId: Int, videoId: String, modifiedTime: DateTime, id: Option[Int] = None)
}
