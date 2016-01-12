package com.pg

import org.apache.spark.sql.hive.test.TestHiveContext
import org.scalatest._

class AppIntTest extends FunSuite with BeforeAndAfterAll with BeforeAndAfter with Matchers {

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

  test("test spark int") {
      sqlContext.sql("""LOAD DATA LOCAL INPATH 'src/test/resources/test.txt'
         OVERWRITE INTO TABLE data.test""")

      val cnt = TestApp.testCount(sqlContext)

      cnt shouldEqual 3
    }
}
