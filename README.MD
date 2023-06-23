# <p align=center>Kitchen Genius</p>
## <p align=center>API Rest de Receitas Culinárias</p>
<p align=center><img src="https://img.shields.io/badge/Java-17-orange.svg" alt="Java versão 17">
<img src="https://img.shields.io/badge/Kotlin-1.8.21-purple.svg" alt="kotlin versão 1.8.21">
<img src="https://img.shields.io/badge/Spring Boot-3.1.0-green.svg" alt="Spring Boot versão 3.1.0">
<img src="https://img.shields.io/badge/Gradle-7.6.1-blue.svg" alt="Gradle versão 7.6.1">
<img src="https://img.shields.io/badge/Docker--cyan.svg" alt="Docker">
</p>

### Instrução de uso

No console/terminal digite:
<ol>
<li>Faça clone do projeto direto do github com o comando: <code>git clone https://github.com/Borriguel/kitchen-genius.git</code></li>
<li>Entre na pasta raiz do projeto</li>
<li>Execute o comando: <code>gradlew bootRun</code></li>
<li>Após a execução acesse: <a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a> para acessar a documentação e acessar os endpoints através do navegador</li>
</ol>
Executando no Docker:
<ol>
<li>Faça clone do projeto direto do github com o comando: <code>git clone https://github.com/Borriguel/kitchen-genius.git</code></li>
<li>Entre na pasta raiz do projeto</li>
<li>Execute o comando: <code>gradlew build</code></li>
<li>Após gerar o build execute o comando <code>docker-compose up -d</code></li>
<li>Após a execução acesse: <a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a> para acessar a documentação e acessar os endpoints através do navegador</li>
</ol>