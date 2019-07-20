Spring Data R2DBC 1.0 M2 and Spring Boot starter released

https://spring.io/blog/2019/05/15/spring-data-r2dbc-1-0-m2-and-spring-boot-starter-released

Note that Spring Data R2DBC is released outside of the [Moore](https://github.com/spring-projects/spring-data-commons/wiki/Release-Train-Moore) release train and it will be part of the next release train [Neumann](https://github.com/spring-projects/spring-data-commons/wiki/Release-Train-Neumann).


### Run the application

1. Start PostgreSQL

```bash
$ docker container run --rm --name reactive-tx-postgres -e POSTGRES_PASSWORD=s3cr3t -d -p 5432:5432 postgres:11.4
```

2. Connect to PostgreSQL

```bash
$ docker container exec -it reactive-tx-postgres bash
root@4abb885d8fd7: psql -h localhost -U postgres -d postgres
psql (11.4 (Debian 11.4-1.pgdg90+1))
Type "help" for help.

postgres=#
```

3 Create the `customers` table

```bash
postgres=# create table customers (id BIGSERIAL, name VARCHAR, PRIMARY KEY(id));
CREATE TABLE
```

