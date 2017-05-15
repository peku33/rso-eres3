FROM openjdk:8
COPY . /usr/eres3/backend/
WORKDIR /usr/eres3/backend/
RUN ./gradlew build -x test
CMD [ "./gradlew", "run" ]
