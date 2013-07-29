name := "SCalaInAction"

scalaVersion := "2.10.2"

libraryDependencies += "commons-logging" % "commons-logging" % "1.1.1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.1.2"

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "2.11.2"


// append options passed to the Scala compiler
scalacOptions ++= Seq("-deprecation", "-unchecked")

retrieveManaged := true