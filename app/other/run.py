import sys
import docker
import os
import time

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
    #TODO: time limit
    #TODO: memory limit
    
    result = container.wait()
    exit_code = result['StatusCode']
    if exit_code == 137:
        print("MEMORY LIMIT EXCEEDED!!")
        return
    elif exit_code != 0:
        print("RUNTIME ERROR!!")
        error_logs = container.logs(stdout=False, stderr=True).decode().strip()
        print(error_logs)
        return
    
    logs = container.logs(stdout=True, stderr=False).decode().strip();
    if(logs == output):
        print("PASSED!!")
    else:
        print("FAILED!!")
    

def main():
    print("**THE ULTIMATE CODE RUNNER**")
    lang = sys.argv[1]
    codePath = sys.argv[2]
    questionPath = sys.argv[3]
    for d in os.listdir(questionPath):
        input = open(questionPath + "/" + d + "/in.txt", "r").read()
        output = open(questionPath + "/" + d + "/out.txt", "r").read()
        run_test(lang, codePath, input, output)

    

if __name__ == "__main__":
    main()
