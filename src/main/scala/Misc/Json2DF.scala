package Misc

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

case class Person(ID: String, NAME: String, LOC: String)

object Json2DF {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .master("local")
      .appName("Test")
      .getOrCreate()

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

    var strList = List.empty[String]
    var jsonString1 = """{"ID" : "111a","NAME":"Arkay","LOC":"Pune"}"""
    var jsonString2 = """{"ID" : "222b","NAME":"Dinesh","LOC":"PCMC"}"""
    strList = strList :+ jsonString1
    strList = strList :+ jsonString2

    import spark.implicits._

    val rddData = spark.sparkContext.parallelize(strList)
    val resultDF = spark.read.json(rddData)
    resultDF.show()


    //val encoder = org.apache.spark.sql.Encoders.product[Person]

    val ds = resultDF.as[Person]

    ds.show()
  }


}