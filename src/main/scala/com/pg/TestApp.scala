package com.pg

import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}


object TestApp {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName(s"Data Validator")
    val sc = new SparkContext(conf)
    val sqlContext = new HiveContext(sc)

    val options = new CliOptions(args.toList)

    import sqlContext.implicits._
  }

  def testCount(sqlContext: HiveContext): Long = {
    val stmt =
      s"""
         SELECT * FROM data.test
       """.stripMargin

    sqlContext.sql(stmt).count()
  }

}
