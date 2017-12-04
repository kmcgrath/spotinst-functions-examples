FROM buildpack-deps:trusty-curl

ENV DEBIAN_FRONTEND noninteractive

RUN locale-gen en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

ENV VERSION 8
ENV UPDATE 131
ENV BUILD 11

ENV MAVEN_MAJOR 3
ENV MAVEN_VERSION 3.5.2

ENV JAVA_HOME /usr/lib/jvm/java-${VERSION}-oracle
ENV JRE_HOME ${JAVA_HOME}/jre

RUN mkdir /app

RUN curl --silent --location --retry 3 --cacert /etc/ssl/certs/GeoTrust_Global_CA.pem \
--header "Cookie: oraclelicense=accept-securebackup-cookie;" \
"http://download.oracle.com/otn-pub/java/jdk/8u131-b11/d54c1d3a095b4ff2b6607d096fa80163/jdk-8u131-linux-x64.tar.gz" | \
tar xz -C /tmp && \
  mkdir -p /usr/lib/jvm && mv /tmp/jdk1.${VERSION}.0_${UPDATE} "${JAVA_HOME}" && \
  apt-get autoclean && apt-get --purge -y autoremove && \
  rm -rf /tmp/* /var/tmp/*

RUN update-alternatives --install "/usr/bin/java" "java" "${JRE_HOME}/bin/java" 1 && \
  update-alternatives --install "/usr/bin/javac" "javac" "${JAVA_HOME}/bin/javac" 1 && \
  update-alternatives --set java "${JRE_HOME}/bin/java" && \
  update-alternatives --set javac "${JAVA_HOME}/bin/javac"

ADD http://apache.mirrors.lucidnetworks.net/maven/maven-${MAVEN_MAJOR}/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz

RUN cd /tmp && tar xf apache-maven-${MAVEN_VERSION}-bin.tar.gz && mv apache-maven-${MAVEN_VERSION} /

ENV PATH ${PATH}:/apache-maven-${MAVEN_VERSION}/bin

WORKDIR /app

