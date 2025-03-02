## Сборка и запуск

**1. Сборка проекта (без тестов) для получения артефакта сборки**

```shell
  mvn install -DskipTests  
```

**2. Запуск проекта с использованием Docker Compose:**

```shell
  docker-compose up --build    
```

---

## Основные эндпоинты API

http://localhost:8080/ адрес веб сайта

- **Пользователи:**
    - `POST /users` — создать пользователя
    - `GET /users/{id}` — получить информацию о пользователе
    - `GET /users` — получить список всех пользователей
    - `PUT /users/{id}` — обновить данные пользователя
    - `DELETE /users/{id}` — удалить пользователя
    - `GET /users` — получить список всех пользователей

- **Подписки:**
    - `POST /users/{id}/subscriptions` — добавить подписку пользователю
    - `GET /users/{id}/subscriptions` — получить подписки пользователя
    - `DELETE /users/{id}/subscriptions/{sub_id}` — удалить подписку
    - `GET /subscriptions/top` — получить ТОП-3 популярных подписок


