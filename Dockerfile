from java

USER root

COPY target/dddsample-2.0-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "dddsample-2.0-SNAPSHOT.jar"]
