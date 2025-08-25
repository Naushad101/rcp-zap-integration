FROM eclipse-temurin:21-jre-alpine
 
WORKDIR /app
 
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app/rcp-backend.jar
 
EXPOSE 8081
 
CMD ["java", "-jar", "rcp-backend.jar"]

# # Stage 1: Build custom JRE using jlink 
# FROM eclipse-temurin:21-jdk-alpine AS jre-builder

# RUN jlink --output /jre --verbose --strip-debug --compress=2 --no-header-files --no-man-pages \
#     # --add-modules java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.unsupported,java.xml,java.scripting,java.net.http,java.logging,java.prefs,java.rmi,java.security.sasl,java.xml.crypto,jdk.crypto.ec,jdk.crypto.cryptoki,jdk.localedata,jdk.zipfs,java.transaction.xa,java.compiler,jdk.management,jdk.management.agent,jdk.httpserver,java.se \
#     --add-modules java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.management.agent,jdk.crypto.cryptoki \
#     && apk add --no-cache binutils \
#     && strip -p --strip-unneeded /jre/lib/server/libjvm.so

# # Stage 2: Final runtime image  
# FROM alpine:3.19.1

# # Environment Variables 
# ENV RENO_CLOUD_PORTAL_HOME=/usr/local/reno-cloud-portal
# ENV RENO_CLOUD_ENV=/usr/local/reno-cloud-env
# ENV JAVA_HOME=${RENO_CLOUD_ENV}/jdk_linux
# ENV PATH=${JAVA_HOME}/bin:${RENO_CLOUD_PORTAL_HOME}:${PATH}

# # Install tools 
# RUN apk update && apk add --no-cache bash dos2unix unzip curl \
#     && mkdir -p ${RENO_CLOUD_PORTAL_HOME} ${RENO_CLOUD_ENV}

# # Create non-root user 
# RUN adduser -S -G root -u 1001 -s /bin/bash bntcnx

# # Copy custom JRE 
# COPY --from=jre-builder --chown=bntcnx:root /jre ${JAVA_HOME}

# # Copy Application Jar and OpenTelemetry Agent 
# COPY --chown=bntcnx:root build/libs/backend-0.0.1-SNAPSHOT.jar ${RENO_CLOUD_PORTAL_HOME}/reno-cloud-app.jar

# ADD --chown=bntcnx:root https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.2.0/opentelemetry-javaagent.jar ${RENO_CLOUD_PORTAL_HOME}/opentelemetry-javaagent.jar

# # Permissions 
# RUN chmod -R 755 "$RENO_CLOUD_ENV" && \
#     chmod -R 777 "$RENO_CLOUD_PORTAL_HOME" && \
#     rm -rf $RENO_CLOUD_ENV/*.zip

# # Switch to non-root user 
# USER bntcnx
# WORKDIR $RENO_CLOUD_PORTAL_HOME

# EXPOSE 8081

# # CMD ["java", "-javaagent:/usr/local/reno-cloud-portal/opentelemetry-javaagent.jar", "-jar", "/usr/local/reno-cloud-portal/reno-cloud-app.war"]
# CMD ["java", "-jar", "/usr/local/reno-cloud-portal/reno-cloud-app.jar", "--debug"]