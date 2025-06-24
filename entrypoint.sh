#!/bin/bash

# Запуск виртуального экрана
Xvfb :99 -screen 0 1366x768x24 &

# Запуск записи экрана
ffmpeg -y -video_size 1366x768 -framerate 15 -f x11grab -i :99 \
  -codec:v libx264 -pix_fmt yuv420p screen_recording.mp4 > /dev/null 2>&1 &
FFMPEG_PID=$!

# Запуск тестов
mvn test -DSEED_PHRASE="${SEED}" -DPASSWORD="${PASS}"

# Остановка записи
kill -INT $FFMPEG_PID
sleep 2

# Копируем видео в Allure
mkdir -p target/allure-results
cp screen_recording.mp4 target/allure-results/
