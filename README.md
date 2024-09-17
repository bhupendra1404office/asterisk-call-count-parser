### Configuration files
file:////home/core/scrap/
file:////home/core/scrap/database.properties


localhost:9001/api/update-data

#### SQL Script
```mysql
CREATE TABLE `incoming_calls_details` (
`server` varchar(255) NOT NULL,
`date` datetime NOT NULL,
`channel_active` int DEFAULT NULL,
`channel_used` int DEFAULT NULL,
`incremental_calls` int DEFAULT NULL,
`last_call_processed` int DEFAULT NULL,
PRIMARY KEY (`server`,`date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```
---------------------------------------------------

application.properties

```bash
server.port=9001
app.server.name=amtel
app.log.file.location=./logs/current_YYYY-DD-MM.log

#Cron every hour
logs.schedular=* 0 * * *

```
----------------------------------------------------

database.properties

````bash

app.datasource.scrap.url=jdbc:mysql://localhost:3306/crm?zeroDateTimeBehavior=convertToNull
app.datasource.scrap.username=root
app.datasource.scrap.password=root
app.datasource.scrap.platform=mysql

spring.jpa.hibernate.ddl-auto=update
hibernate.transaction.jta.platform=org.hibernate.service.jta.platform.internal.JBossAppServerJtaPlatform
````