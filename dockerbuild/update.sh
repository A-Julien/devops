#!/bin/bash

arches="files"

print_header() {
	cat > $1 <<-EOI
	# DevOps
	#
	# ------------------------------------------------------------------------------
	#               NOTE: THIS DOCKERFILE IS GENERATED VIA "update.sh"
	#
	#                       PLEASE DO NOT EDIT IT DIRECTLY.
	# ------------------------------------------------------------------------------
	EOI
}

# Print selected image
print_baseimage() {
	cat >> $1 <<-EOI
	FROM openjdk:8u171-alpine3.7
	LABEL maintainer = "julien.alaimo@gmail.com"
	EOI
}

# Print metadata && basepackages
print_basepackages() {
	cat >> $1 <<-'EOI'	
	ENV \
    	INITSYSTEM on \
    	DEBIAN_FRONTEND=noninteractive \
	# Basic build-time metadata as defined at http://label-schema.org
	ARG BUILD_DATE
	ARG VCS_REF
	ARG PROJECT_VERSION=default_value
	ARG HOME=default_value
	ENV PROJECT_VERSION=$PROJECT_VERSION
	ENV HOME=$HOME

	LABEL org.label-schema.build-date=$BUILD_DATE \
    	org.label-schema.docker.dockerfile="/Dockerfile" \
    	org.label-schema.name="devOps"
	#--------------Install basepackages--------------#



	RUN apk add --update ca-certificates && \
        find /usr/share/ca-certificates/mozilla/ -name "*.crt" -exec keytool -import -trustcacerts \
        -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts -storepass changeit -noprompt \
        -file {} -alias {} \; && \
        keytool -list -keystore /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/cacerts --storepass changeit && \
        apk add --no-cache bash && \
        rm -rf /var/cache/apk/* && \
        rm -rf /var/lib/apt/lists/ */tmp/* /var/tmp/*

	ENV MAVEN_VERSION 3.5.4
	ENV MAVEN_HOME /usr/lib/mvn
	ENV PATH $MAVEN_HOME/bin:$PATH

	RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
      tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
      rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
      mv apache-maven-$MAVEN_VERSION /usr/lib/mvn
EOI
}


# Set working directory and execute command
print_command() {
	cat >> $1 <<-'EOI'
	# Execute command

	COPY ./devops-${PROJECT_VERSION}-jar-with-dependencies.jar /app/devops-${PROJECT_VERSION}-jar-with-dependencies.jar
	COPY ./data.csv /app/data.csv
	CMD java -jar /app/devops-${PROJECT_VERSION}-jar-with-dependencies.jar
EOI
}

# Build the Dockerfiles
for arch in ${arches}
do
	file=gen_dockerfile/${arch}/Dockerfile
		mkdir -p `dirname ${file}` 2>/dev/null
		echo -n "Writing $file..."
		print_header ${file};
		print_baseimage ${file};
		print_basepackages ${file};
		print_command ${file};
		echo "done"
done