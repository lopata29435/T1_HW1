# Инструкция к запуску

## 1. Сборка модулей проекта

```bash
./gradlew :consumer-service:shadowJar :producer-service:shadowJar
```

## 2. Сборка Docker-compose файла

```bash
docker-compose up --build -d
```

## 3. Запуск

Далее запускаете любым удобным для вас образом, через консоль или IDE.