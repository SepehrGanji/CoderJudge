import sys
import docker
import os
import threading


client = docker.from_env()

def run_test(lang, codePath, input, output):
    volumes = {codePath: {'bind': '/tmp', 'mode': 'ro'}}
    img = ""
    cmd = ""
    if lang == "python":
        img = "python:3.10"
        cmd = f"bash -c 'echo \"{input}\" | python /tmp/code.py'"
    elif lang == "cpp":
        img = "gcc:4.9"
        cmd = f"bash -c 'echo \"{input}\" | /tmp/app'"
    
    container = client.containers.run(
        img,
        cmd,
        volumes=volumes,
        detach=True,
        mem_limit='7m',
    )
    def handle_container():
        container.reload()
        if container.status == 'running':
            print("TIME-LIMIT")
            container.kill()
        else:
            result = container.wait()
            exit_code = result['StatusCode']
            if exit_code == 137:
                print("MEMORY-LIMIT")
            elif exit_code != 0:
                print("RUNTIME-ERROR")
            else:
                logs = container.logs(stdout=True, stderr=False).decode().strip();
                if(logs == output):
                    print("ACCEPT")
                else:
                    print("WRONG-ANSWER")
    
    timer = threading.Timer(2, handle_container)
    timer.start()
    
def main():
    lang = sys.argv[1]
    codePath = sys.argv[2]
    questionPath = sys.argv[3]
    for d in os.listdir(questionPath):
        input = open(questionPath + "/" + d + "/in.txt", "r").read()
        output = open(questionPath + "/" + d + "/out.txt", "r").read()
        run_test(lang, codePath, input, output)

if __name__ == "__main__":
    main()
