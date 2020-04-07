cd /d %~dp0
call npm install
call npm run build:stage
call mvn wagon:upload