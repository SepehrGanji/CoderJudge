# Coder Judge

## About project
Coder Judge is an open-source platfrom written in Java and Python to compile and run code submissions against test-cases. It uses RabbitMQ to fetch pending jobs, compiles them and then runs agains test cases.
After judging, one of these verdics are possible for the code.
- `COMPILE-ERROR`
- `ACCEPT`
- `WRONG-ANSWER`
- `RUNTIME-ERROR`
- `TIME-LIMIT`
- `MEMORY-LIMIT`

Currently, we support `C++` and `Python` as the submission languages. You can add other languages by simply adding compiler in `app/other/compile.py` and modifying the runner script in `app/other/run.py`.

## Project Setup

You need `java` and `gradle` on your system to run this project.
### Environment file pattern
The `.env` file must be placed in the root directory following the pattern described below.
```
DB_URL=                                # database host address (including port)
DB_USER=                             # database username
DB_PASSWD=                       # database password

QUEUE_HOST=                     # queue host address (including port)
QUEUE_USER=                      # queue username 
QUEUE_PASSWD=                # queue password
SUBMISSION_QUEUE_NAME=     # queue name for submissions
RESULT_QUEUE_NAME=               # queue name for results

FILE_PATH=                           # root filesystem path
```

### Setup envirounment variables
You need to set-up every variable on your `.env` file to your current envirounment. This can simply done with Docker, or on `bash` with:
```
$ export $(grep -v '^#' .env | xargs)
```
### Run the project
```
./gradlew run
```