set CMD_LINE_ARGS=%*
set SCRIPT_PATH=%~dp0
set GRADLE_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=9999,server=y,suspend=y"
%SCRIPT_PATH%/gradlew --gradle-user-home=c:\gradle %CMD_LINE_ARGS%

