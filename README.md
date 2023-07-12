# Filmorate Microservices

Бэкенд сервиса по поиску фильмов. 
Написан с использованием микросервисной архитектуры.
Позволяет проводить базовые CRUD операции с пользователями, фильмами, отзывами к фильмам, режиссерами.
Также позволяет добавлять и удалять друзей, ставить лайки фильмам и отзывам, получать рекоммендации по фильмам, получать
список общих друзей, получать ленту событий пользователя. 

## Стек технологий
- Spring Boot 2
- Kafka
- Zookeeper
- Spring Cloud Config Server
- Spring Cloud Netflix Eureka Server
- Spring Cloud OpenFeign
- Spring Data Jpa
- PostgreSQL
- Kotlin
- Flyway Migrations
- Spring Cloud Loadbalancer
- Spring Cloud Gateway Server
- TestContainers
- OpenApi

## Общая схема

## Архитектура
### Gateway
В качестве единой точки входа запросов от пользователей является Gateway Service, реализованный на основе Spring Cloud Gateway.
По настроенным правилам, он маршрутизирует запросы в нужные микросервисы. 

### Configs
Конфигурации всех микросервисов хранятся на отдельном сервере Config Server, реализованном на основе Spring Cloud Config. 
На текущий момент, конфигурации хранятся локально на этом же сервере в папке config. В дальнейшем планируется перенести их в 
отдельный репозиторий на Github.

### Discovery
SМеханизм Service Discovery реализован на основе Spring Cloud Netflix Eureka Server. Все микросервисы регистрируются в нем после запуска,
таким образом имеется возможность поднять несколько экземпляров одного микросервиса и через Service Discovery обращаться к ним. 

### Messaging
В качестве брокера сообщений используется Apache Kafka (версия с Zookeeper).

### API Documentation
Каждый микросервис имеет свою API документацию в виде OpenAPI.   

### Хранилище данных
Каждый микросервис хранит данные в собственной базе Postgres. 

### Микросервисы
#### Film Service
Хранит информацию о фильмах, режиссерах, жанрах, рейтингах MPA. 
Предоставляет API для CRUD операций с вышеуказанными сущностями.
Также предоставляет API для получения популярных фильмов, фильмов по режиссерам, общих фильмов для двуй пользователей,
поиску фильмов, добавлению и удалению лайков. 

При добавлении или удалении лайка, в topic Kafka - filmLikes отправляется соответствующее событие. 
Реитинг фильма при создании равен 0. Однако пользователи могут его менять, проставляя лайки с оценками.
За обновление рейтинга отвечает сервис rating service. Он вычитывает сообщения о добавлении / удалении лайка
из Kafka, сохраняет информацию в базе и потом отправляет сообщение с новым рейтингом в соответствующий
topic Kafka - newRatings. Film-Service вычитывает сообщения из этого топика и обновляет рейтинг фильма в своей базе.
Взаимодействие с Kafka реализовано на основе EventListeners. Kafka Consumer не сохраняет информацию в БД, а
публикует событие с необходимой инормацией, слушатель этого события в свою очередь уже взаимодействует с базой. 
Это позволяет не терять время при коммите оффсета в топике. Тем не менее, проблему атомарности вычитвания
сообщений из Кафки и сохранения данных в бызу это не решает. В дальнейшем планируется использовать Kafka Connect
для переноса данных из Кафки в базу и из базы в Кафку, в частности Debezium коннектор для работы с Postgres. 

Кроме того сервис синхронно взаимодействует с микросервисами Rating Service и User Service с 
помощью FeignClient. Синхронное взаимодействие необходимо для следующий сценариев:
- Проверка наличия пользователя
- Получение списка идентификаторов фильмов, которым пользователь ставил оценку
- Получение списка идентификаторов фильмов, рекомендованных пользователю.

#### User Service
Хранит информацию о пользователях и друзьях. 
Предоставляет API для CRUD операций с пользователями. 
Также предоставляет API для получения списка друзей пользователя, получения общих друзей двух пользователей,
получения списка фильмов, рекомендованных для просмотра пользователю, получения ленты событий пользователя.

Лента событий на текущий момент статическая, в дальнейшем предполагается использование Server Side Events.

При добавлении / удалении пользователя публикуется соответствующее событие, слушатель которого 
отправляет в Кафку сообщение. Это событие учитывается в ленте событий пользователя.

#### Reviews Service
Хранит информацию об отзывах пользователей.
Предоставляет API для CRUD операций с отзывами. Также предоставляет API для добавления / удаления 
лайка / дизлайка отзыву. 

При создании / обновлении / удалении отзыва публикуется соответствующее событие, слушатель которого
отправляет в Кафку сообщение, которое участвует в формировании ленты событий пользователя. 

#### Rating Service
Хранит информацию об оценках, которые пользователи ставят фильмам. Эта информация вычитывается 
из соответствующего топика Кафки и сохраняется в базу. 

Предоставляет API для:
- получения списка идентификаторов фильмов, которым пользователь ставил оценку
- получения рейтинга фильма
- получения списка идентификаторов фильтмов, рекомендованных пользователю

Алгоритм рекомендаций фильмов:
1. Получить идентификатор пользователя, который максимально пересекается по количеству положительных оценок фильмам с текущим пользователем
2. Выбрать те фильмы, которым полученный в шаге 1 пользователь ставил положительные оценки, а текущий пользователь вообще не оценивал.

#### Events Service
Хранит информацию о событиях, совершаемых пользователями:
- Добавление / удаление друзей
- Добавление / Удаление оценки фильму
- Добавление / удаление / обновление отзыва фильму

Предоставляет API для получения списка событий пользователя. 

События вычитываются из топиков Кафки и сохраняются в базу данных. 