import sys
import docker

client = docker.from_env()

def main():
    print("**THE ULTIMATE CODE COMPILER**")
    lang = sys.argv[1]
    path = sys.argv[2]
    if lang == "python":
        print("Python code, no compilation needed!")
        sys.exit(0)
    elif lang == "cpp":
        print("Compiling C++ code...")
        volumes = {path: {'bind': '/tmp', 'mode': 'rw'}}
        container = client.containers.run(
            "gcc:4.9",
            "g++ /tmp/code.cpp -o /tmp/app",
            volumes=volumes,
            detach=True
        );
        result = container.wait()
        logs = container.logs()
        with open(path + "/logs.txt", "w") as f:
            f.write(logs.decode())
        container.remove()
        sys.exit(result['StatusCode'])

if __name__ == "__main__":
    main()
