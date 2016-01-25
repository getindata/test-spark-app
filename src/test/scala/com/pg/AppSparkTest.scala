package com.pg

import org.apache.spark.sql.hive.test.TestHiveContext
import org.scalatest._

class AppSparkTest extends FunSuite with BeforeAndAfterAll with BeforeAndAfter with Matchers {

  var sqlContext: TestHiveContext = _

  override def beforeAll() {
    sqlContext = SparkContextFactory.getSqlContext
    sqlContext.sql( """CREATE DATABASE IF NOT EXISTS data""")
    sqlContext.sql( """DROP TABLE IF EXISTS data.test""")
    sqlContext.sql( """
        CREATE TABLE IF NOT EXISTS data.test (
                      line STRING
        )
        ROW FORMAT DELIMITED
        FIELDS TERMINATED BY ','
        STORED AS TEXTFILE
                    """)
  }

  test("test with Hive") {
      sqlContext.sql("""LOAD DATA LOCAL INPATH 'src/test/resources/test.txt'
         OVERWRITE INTO TABLE data.test""")

      val cnt = TestApp.testCountHive(sqlContext)

      cnt shouldEqual 3
    }

  test("test RDD") {
      val rdd = sqlContext.sparkContext.parallelize(Array(1, 2, 3, 4))
      TestApp.testCountRDD(rdd) shouldEqual 4
  }
}
