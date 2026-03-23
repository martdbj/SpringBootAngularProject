import subprocess

angular_exec = subprocess.Popen(["ng", "serve", "-o"], cwd="./frontend", shell=True)
if angular_exec == 0:
    print("Angular application initialized")
else:
    print("Angular application failed to start")

docker_exec = subprocess.Popen(["docker", "compose", "up", "-d"], cwd="./backend")
if docker_exec == 0:
    print("Docker containers initialized")
else:
    print("Docker containers failed to start")

spring_exec = subprocess.Popen([".\\mvnw.cmd", "clean", "spring-boot:run"], cwd="./backend/backend", shell=True)
if docker_exec == 0:
    print("Spring Boot application initialized")
else:
    print("Spring Boot application failed to start")
