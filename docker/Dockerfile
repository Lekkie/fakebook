FROM centos:latest
FROM java:8
MAINTAINER Lekan Omotayo <lekkie.aydot@gmail.com>

ADD VERSION .

ARG FAKEBOOK_VERSION=1.0-SNAPSHOT

EXPOSE 8080

VOLUME /tmp

ADD application.yml /root/blowfish/application.yml
ADD fakebook-1.0-SNAPSHOT.jar /root/fakebook/fakebook.jar
RUN bash -c 'touch /root/fakebook/fakebook.jar'
ADD install.sh /root/fakebook/install.sh
RUN bash -c '/root/fakebook/install.sh'

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/root/fakebook/fakebook.jar", "--spring.config.location=/root/fakebook/application.yml"]



