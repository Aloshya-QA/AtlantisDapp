FROM selenium/standalone-chrome-debug:latest

USER root

# Установка Java, Maven, ffmpeg
RUN apt-get update && \
    apt-get install -y maven openjdk-17-jdk ffmpeg unzip git && \
    apt-get clean

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH
ENV DISPLAY=:99

# Рабочая директория
WORKDIR /app

# Копируем все файлы в контейнер
COPY . .

# Сборка проекта
RUN mvn clean compile
