FROM adoptopenjdk/openjdk8-openj9 AS build-stage

RUN apt-get update && \
    apt-get install -y maven unzip

COPY . /project
WORKDIR /project

#RUN mvn -X initialize process-resources verify => to get dependencies from maven
#RUN mvn clean package	
#RUN mvn --version
RUN mvn clean package

RUN mkdir -p /config/apps && \
    mkdir -p /sharedlibs && \
    cp ./src/main/liberty/config/server.xml /config && \
    cp ./target/*.*ar /config/apps/ && \
    if [ ! -z "$(ls ./src/main/liberty/lib)" ]; then \
        cp ./src/main/liberty/lib/* /sharedlibs; \
    fi


FROM openliberty/open-liberty:kernel-slim-java8-openj9-ubi as staging

RUN mkdir -p /opt/ol/wlp/usr/shared/config/lib/global

COPY --chown=1001:0 --from=build-stage /config/ /config/
COPY --chown=1001:0 --from=build-stage /sharedlibs/ /opt/ol/wlp/usr/shared/config/lib/global

# This script will add the requested XML snippets to enable Liberty features and grow image to be fit-for-purpose using featureUtility
RUN features.sh

COPY --chown=1001:0 --from=build-stage /config/apps/*.jar /staging/myFatApp.jar

RUN springBootUtility thin \
 --sourceAppPath=/staging/myFatApp.jar \
 --targetThinAppPath=/staging/myThinApp.jar \
 --targetLibCachePath=/staging/lib.index.cache


FROM openliberty/open-liberty:kernel-slim-java8-openj9-ubi

COPY --chown=1001:0 --from=staging /config/ /config/

# This script will add the requested XML snippets to enable Liberty features and grow image to be fit-for-purpose using featureUtility
RUN features.sh

COPY --from=staging /staging/lib.index.cache /lib.index.cache
COPY --from=staging /staging/myThinApp.jar /config/dropins/spring/myThinApp.jar


ARG VERBOSE=false
# This script will add the requested server configurations, apply any iFixes and populate caches to optimize runtime
RUN configure.sh