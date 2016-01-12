name := "test-spark-app"

fork :=true

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.datanucleus" % "datanucleus-api-jdo" % "3.2.6" % "provided",
  "org.datanucleus" % "datanucleus-rdbms" % "3.2.9" % "provided",
  "org.datanucleus" % "datanucleus-core" % "3.2.10" % "provided",
  "org.scalatest" % "scalatest_2.10" % "2.2.1",
  "org.apache.spark" % "spark-core_2.10" % "1.5.1",
  "org.apache.spark" % "spark-sql_2.10" % "1.5.1",
  "org.apache.spark" % "spark-hive_2.10" % "1.5.1",
  "log4j" % "log4j" % "1.2.15" exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri"),
  "me.lessis" %% "courier" % "0.1.3"
)

excludedJars in assembly <<= (fullClasspath in assembly) map { cp =>
 cp filter {x => x.data.getName.matches("sbt.*") || x.data.getName.matches(".*macros.*") || x.data.getName.matches("spark.*")}
}
