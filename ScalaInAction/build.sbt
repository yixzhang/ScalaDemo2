name := "SCalaInAction"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
	"commons-logging" % "commons-logging" % "1.1.1",
	"org.apache.httpcomponents" % "httpclient" % "4.1.2",
	"org.mongodb" % "mongo-java-driver" % "2.11.2",
	"com.typesafe.akka" % "akka-actor_2.10" % "2.2.0",
	"com.typesafe" % "config" % "1.0.2",
	"org.scalacheck" %% "scalacheck" % "1.10.0" % "test")

// append options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-unchecked")

javacOptions ++= Seq("-encoding", "UTF-8") 

retrieveManaged := true
