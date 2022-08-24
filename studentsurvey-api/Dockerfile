#TO CONTAINERIZE AN APPLICATION USING A WAR FILE ON DOCKERHUB. 

FROM tomcat:10.0.20-jdk17-corretto

RUN rm -rf /usr/local/tomcat/webapps/*

COPY studentsurvey-api.war /usr/local/tomcat/webapps/