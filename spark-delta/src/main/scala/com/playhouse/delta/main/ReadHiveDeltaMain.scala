package com.playhouse.delta.main

import com.playhouse.delta.infra.spark.SparkSessionBuilder
import com.playhouse.delta.sparkAppLogger
import org.apache.spark.sql.SparkSession

import scala.util.{Failure, Success, Try}

object ReadHiveDeltaMain {
  def main(args: Array[String]): Unit = {
    Try{
      implicit val spark: SparkSession = SparkSessionBuilder().build(appName = "spark-delta")
      sparkAppLogger.info("Got spark...")

      val hotel = spark.sql("select * from delta.hotel where day>20201010")
      hotel.show(10, true)

    } match {
      case Success(_) => sparkAppLogger.info("Calling from finish job...SUCCESSSSSSS")
      case Failure(e) =>
        sparkAppLogger.error(s"OH NO... Things went wrong, Here exactly --> ${e.getMessage} ${e.getStackTrace.mkString("\n")}")
        // TODO : for debugging.
        e.printStackTrace()
        System.exit(-1)
    }
  }
}
