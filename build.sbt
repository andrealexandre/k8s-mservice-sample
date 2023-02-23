lazy val akkaHttpVersion = "10.5.0"
lazy val akkaVersion    = "2.7.0"

enablePlugins(JavaServerAppPackaging)

Docker / packageName := "k8s-mservice"
dockerExposedPorts ++= Seq(8080)


lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization    := "dev.andrealexandre",
      scalaVersion    := "2.13.4"
    )),
    name := "K8s MService Sample",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"                % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json"     % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed"         % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
      "ch.qos.logback"    % "logback-classic"           % "1.2.11",

      "com.typesafe.akka" %% "akka-http-testkit"        % akkaHttpVersion % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion     % Test,
      "org.scalatest"     %% "scalatest"                % "3.2.9"         % Test
    )
  )
