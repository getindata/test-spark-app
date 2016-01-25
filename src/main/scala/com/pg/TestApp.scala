package com.pg

import org.apache.spark.rdd.RDD
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

  def testCountHive(sqlContext: HiveContext): Long = {
    val stmt =
      s"""
         SELECT * FROM data.test
       """.stripMargin

    sqlContext.sql(stmt).count()
  }

  def testCountRDD(rdd: RDD[Int]): Long = {
      rdd.count()
  }

}
