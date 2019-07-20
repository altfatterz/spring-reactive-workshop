```bash
http post :8080/v1/customers\?names=John\&names=Jane
```

In the logs:

```bash
...
2019-07-20 14:37:49.525 DEBUG 12233 --- [ctor-http-nio-4] o.s.w.s.adapter.HttpWebHandlerAdapter    : [ad63c522] HTTP POST "/v1/customers?names=John&names=Jane"
2019-07-20 14:37:49.526 DEBUG 12233 --- [ctor-http-nio-4] s.w.r.r.m.a.RequestMappingHandlerMapping : [ad63c522] Mapped to com.example.CustomerRestController#create1(String[])
2019-07-20 14:37:49.539 DEBUG 12233 --- [ctor-http-nio-4] o.s.w.r.r.m.a.ResponseBodyResultHandler  : Using 'application/json' given [*/*] and supported [application/json, application/*+json, text/event-stream]
2019-07-20 14:37:49.539 DEBUG 12233 --- [ctor-http-nio-4] o.s.w.r.r.m.a.ResponseBodyResultHandler  : [ad63c522] 0..N [com.example.Customer]
2019-07-20 14:37:49.545 DEBUG 12233 --- [ctor-http-nio-4] o.s.d.r.c.R2dbcTransactionManager        : Creating new transaction with name [null]: org.springframework.transaction.StaticTransactionDefinition@18499de9
2019-07-20 14:37:49.549 DEBUG 12233 --- [ctor-http-nio-4] o.s.d.r.c.R2dbcTransactionManager        : Acquired Connection [MonoMap] for R2DBC transaction
2019-07-20 14:37:49.572 DEBUG 12233 --- [ctor-http-nio-4] o.s.d.r2dbc.core.DefaultDatabaseClient   : Executing SQL statement [INSERT INTO customers (name) VALUES ($1)]
2019-07-20 14:37:49.576 DEBUG 12233 --- [ctor-http-nio-4] o.s.d.r2dbc.core.DefaultDatabaseClient   : Executing SQL statement [INSERT INTO customers (name) VALUES ($1)]
2019-07-20 14:37:49.580 DEBUG 12233 --- [ctor-http-nio-4] o.s.d.r.c.R2dbcTransactionManager        : Initiating transaction commit
2019-07-20 14:37:49.580 DEBUG 12233 --- [ctor-http-nio-4] o.s.d.r.c.R2dbcTransactionManager        : Committing R2DBC transaction on Connection [io.r2dbc.h2.H2Connection@689c1dff]
2019-07-20 14:37:49.583 DEBUG 12233 --- [ctor-http-nio-4] o.s.d.r.c.R2dbcTransactionManager        : Releasing R2DBC Connection [io.r2dbc.h2.H2Connection@689c1dff] after transaction
2019-07-20 14:37:49.583 DEBUG 12233 --- [ctor-http-nio-4] o.s.http.codec.json.Jackson2JsonEncoder  : [ad63c522] Encoding [[Customer(id=3, name=John), Customer(id=4, name=Jane)]]
2019-07-20 14:37:49.584 DEBUG 12233 --- [ctor-http-nio-4] o.s.w.s.adapter.HttpWebHandlerAdapter    : [ad63c522] Completed 200 OK
...

```    