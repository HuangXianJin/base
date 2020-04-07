cd /d %~dp0
call svn update
cd base-ui/base-ui-admin
call build-prod.bat
cd /d %~dp0
call mvn clean install -Dmaven.test.skip=true -Pdev
echo f |xcopy base-application\target\base-application.jar  dist\base-application.jar /S /E /Y
cd dist
call restart
pause