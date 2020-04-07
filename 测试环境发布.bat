cd /d %~dp0
cd base-ui/base-ui-admin
call build-prod.bat
cd /d %~dp0
call mvn clean install -Dmaven.test.skip=true -Ptest
pause