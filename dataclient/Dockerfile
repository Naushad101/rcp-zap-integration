FROM eclipse-temurin:21-jdk-alpine AS jre-builder

RUN jlink --output /jre --verbose --strip-debug --compress=2 --no-header-files --no-man-pages \
    --add-modules java.base,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,jdk.management.agent,jdk.crypto.cryptoki \
    && apk add --no-cache binutils \
    && strip -p --strip-unneeded /jre/lib/server/libjvm.so

FROM alpine:3.19.1

ENV RENO_DATACLIENT_HOME=/usr/local/reno-dataclient
ENV RENO_DATACLIENT_ENV=/usr/local/reno-dataclient-env
ENV JAVA_HOME=${RENO_DATACLIENT_ENV}/jdk_linux
ENV PATH=${JAVA_HOME}/bin:${RENO_DATACLIENT_HOME}:${PATH}

RUN apk update && apk add --no-cache bash dos2unix unzip curl \
    && mkdir -p ${RENO_DATACLIENT_HOME} ${RENO_DATACLIENT_ENV}

RUN adduser -S -G root -u 1001 -s /bin/bash bntcnx

COPY --from=jre-builder --chown=bntcnx:root /jre ${JAVA_HOME}

COPY --chown=bntcnx:root build/libs/dataclient-0.0.1-SNAPSHOT.jar ${RENO_DATACLIENT_HOME}/reno-dataclient.jar

ADD --chown=bntcnx:root https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.2.0/opentelemetry-javaagent.jar ${RENO_DATACLIENT_HOME}/opentelemetry-javaagent.jar

# Permissions 
RUN chmod -R 755 "$RENO_DATACLIENT_ENV" && \
    chmod -R 777 "$RENO_DATACLIENT_HOME" && \
    rm -rf $RENO_DATACLIENT_ENV/*.zip

# Switch to non-root user 
USER bntcnx
WORKDIR $RENO_DATACLIENT_HOME

EXPOSE 8081
 
# CMD ["java", "-javaagent:/usr/local/reno-dataclient/opentelemetry-javaagent.jar", "-jar", "/usr/local/reno-dataclient/reno-dataclient.jar"]
CMD ["java", "-jar", "/usr/local/reno-dataclient/reno-dataclient.jar"]