FROM selenium/standalone-chrome-debug:latest

USER root

# Установка Java, Maven, ffmpeg и прочих утилит
RUN apt-get update && \
    apt-get install -y maven openjdk-17-jdk ffmpeg unzip git && \
    apt-get clean

# Установка переменных окружения
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH
ENV DISPLAY=:99

# Создание рабочей директории
WORKDIR /app

# Копируем проект и скрипт запуска
COPY . /app

# Убедимся, что скрипт entrypoint исполняемый
RUN chmod +x /app/entrypoint.sh

# Компилируем проект (можно оставить только `mvn clean` если test будет позже)
RUN mvn clean compile

# Указываем точку входа
ENTRYPOINT ["./entrypoint.sh"]
