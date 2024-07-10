# Asterisk 

This project solve the business logic of counting logs *app.log.file.location*  to count *activeChannels, maxActiveCalls, callsProcessed*

## Configuration

- Database properties
- PATH : file:////home/core/scrap/database.properties
```properties
app.datasource.scrap.url=jdbc:mysql://localhost:3306/crm?zeroDateTimeBehavior=convertToNull
app.datasource.scrap.username=root
app.datasource.scrap.password=root
app.datasource.scrap.platform=mysql
```

## update api
#### Api to update Database regarding the count
```curl
curl --location '${PROJECT-URL}/api/update-data'
```