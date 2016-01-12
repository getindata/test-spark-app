package com.pg

import org.apache.spark.sql.hive.test.TestHiveContext
import org.apache.spark.{SparkConf, SparkContext}

object SparkContextFactory {

  val conf = new SparkConf().set("spark.driver.allowMultipleContexts", "true")
  val sc: SparkContext = new SparkContext("local[4]", "sc", conf)
  val sqlContext: TestHiveContext = new TestHiveContext(sc)

  def getSqlContext = sqlContext
  def getSparkContext = sc

}
