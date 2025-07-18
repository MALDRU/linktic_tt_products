# linktic_tt_products
Prueba tecnica 17/07/2025 linktic (ms productos)

## Requisitos
- Java 17
- gradle 8.10.2
- docker

## formateador 
- spotless

## Como ejecutar

### Mediante script sh
```bash
sh build-and-up.sh
```

### Build
```bash
gradle build
```

## docker-compose
```bash
docker-compose up -d
```


### Run
```bash
gradle bootRun
```

## Docker
### Build image docker

```bash
docker build -f Dockerfile -t linktic/products:0.0.0 . --no-cache
```

### Run image docker

```bash
docker run -p 8080:8080 --env APP_DB_DSN= --env APP_DB_PASSWORD= --env APP_DB_USER= linktic/products:0.0.0
```

## Documentación

### Swagger
http://localhost:8000/products/api/swagger-ui/index.html#/

### Coleccion postman

[linktic_products.postman_collection.json](linktic_products.postman_collection.json)

### diagrama general
[Digrama general](/diagrama_general.png)