FROM carsharing/alpine-oraclejdk8-bash
COPY build/libs/springboot-activiti.jar springboot-activiti.jar
CMD ["java", "-jar", "springboot-activiti.jar"]
