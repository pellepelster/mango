set CMD_LINE_ARGS=%*
set SCRIPT_PATH=%~dp0
set GRADLE_OPTS="-Dgwt.codeserver.port=9876"
%SCRIPT_PATH%/gradlew --gradle-user-home c:\gradle %CMD_LINE_ARGS%

