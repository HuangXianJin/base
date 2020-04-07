cd /d %~dp0
cd base-ui/base-ui-admin
call build-prod.bat
cd /d %~dp0
call mvn clean install -Dmaven.test.skip=true -Pprod
echo f |xcopy base-application\target\base-application.jar  dist\base-application.jar /S /E /Y
cd dist
call restart
pause