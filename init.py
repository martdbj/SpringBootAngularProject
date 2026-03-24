import os
import subprocess

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

path_frontend = os.path.join(BASE_DIR, "frontend")
path_backend_root = os.path.join(BASE_DIR, "backend")
path_spring_app = os.path.join(BASE_DIR, "backend", "backend")

print("Installing angular project packages...")
subprocess.call("npm install", cwd=path_frontend, shell=True)
print("Project pacakges installed")


subprocess.Popen(["docker", "compose", "up", "-d"], cwd=path_backend_root)


subprocess.Popen([".\\mvnw.cmd", "clean", "spring-boot:run"], cwd=path_spring_app, shell=True)


subprocess.Popen(["ng", "serve", "-o"], cwd=path_frontend, shell=True)