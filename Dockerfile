# Imagem base com Java 21
FROM eclipse-temurin:21-jdk-alpine

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado
COPY target/netflix-0.0.1-SNAPSHOT.jar netflix.jar

# Expõe a porta 8080
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "netflix.jar"]