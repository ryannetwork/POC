name := "poc"

version := "0.1"

scalaVersion := "2.11.8"
val sparkVersion = "2.4.0"
val javacppVersion = "1.1"
val tessVersion = "3.4.2"

resolvers += "mvnrepository" at "http://mvnrepository.com/artifact/"

//libraryDependencies += "net.sourceforge.tess4j" % "tess4j" % "4.3.1"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.0"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.0"
// https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.2.0"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql-kafka-0-10
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.4.0"
//libraryDependencies += "org.ghost4j" % "ghost4j" % "1.0.1"
//libraryDependencies +=  "net.sourceforge.tess4j" % "tess4j" % tessVersion
libraryDependencies += "org.apache.pdfbox" % "pdfbox" % "2.0.8"
libraryDependencies += "org.bytedeco.javacpp-presets" % "tesseract-platform" % "3.04.01-1.3"
libraryDependencies += "org.apache.pdfbox" % "pdfbox-tools" % "2.0.8"


//excludeDependencies += "org.apache.logging.log4j" % "log4j-slf4j-impl"
