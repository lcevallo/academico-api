@echo off

: Sets the proper date and time stamp with 24Hr Time for log file naming
: convention
set anio=%date:~-4,4%
set mes=%date:~-7,2%
set dia=%date:~-10,2%
set Milliseconds= %time:~-2,2%
set Seconds= %time:~-5,2%
set Minutes= %time:~-8,2%
set Hours= %time:~-11,2%

set final_archivo=%anio%%mes%%dia%%Hours%%Minutes%%Seconds%
ECHO %final_archivo%
c:
cd "C:\Users\Administrator\Documents\NetBeansProjects\attinae\academico-api\src\main\resources\sql\backup"
dir *.dump

echo Este batch cargara la nueva base de datos de casos
pause
c:
cd "C:\Program Files\PostgreSQL\9.6\bin"
set /p nombre_archivo=Ingrese el nombre del respaldo: 

rem psql -h 192.168.10.80 -p 5432 -U postgres < C:\Users\Luis Cevallos\Documents\NetBeansProjects\academico\src\main\resources\sql\%nombre_archivo%.dump

: Esto es para Linux
rem psql -d attinae_db -h localhost -p 5432 -U postgres < /root/Downloads/attinae_db-2016-11-02-1519.backup

: Esto es para Windows
rem psql -h 201.183.230.79 -p 5432 -U postgres < C:\Users\luigi\Documents\NetBeansProjects\academico\src\main\resources\sql\%nombre_archivo%.dump
psql -h localhost -p 5432 -U postgres < "C:\Users\Administrator\Documents\NetBeansProjects\attinae\academico-api\src\main\resources\sql\backup\"%nombre_archivo%.dump
pause
echo Espero se haya restaurado la base de datos
pause
exit

