@echo off

echo Este batch hara un respaldo de la base de datos htmc
pause
set RutaPostgres="C:\Program Files\PostgreSQL\9.6\bin"

: Sets the proper date and time stamp with 24Hr Time for log file naming
: convention
set anio=%date:~-4,4%
set mes=%date:~-7,2%
set dia=%date:~-10,2%
set Milliseconds= %time:~-2,2%
set Seconds= %time:~-5,2%
set Minutes= %TIME:~3,2%
set Hours=%time:~0%
set Hours=%Hours:~0,2%

set hr=%TIME: =0%
set hr=%hr:~0,2%
echo %hr%

set min=%TIME:~3,2%
echo %min%

set sec=%TIME:~6,2%
echo %sec%


set nombre_archivo=attinae-%anio%-%mes%-%dia%-%hr%%min%LACC

set final_archivo=%anio%%mes%%dia%%Hours%%Minutes%%Seconds%
ECHO %final_archivo%
c:

cd "C:\Users\Luis Cevallos\Documents\NetBeansProjects\academico\src\main\resources\sql\backup"

dir *.dump

rem set /p nombre_archivo=Ingrese el nombre del respaldo: 
ECHO %nombre_archivo%
c:
cd "C:\Program Files\PostgreSQL\9.6\bin"
pause

pg_dump -h localhost -p 5432 -U postgres -C -f "C:\Users\Administrator\Documents\NetBeansProjects\attinae\academico-api\src\main\resources\sql\backup\%nombre_archivo%.dump" attinae_db
rem pg_dump -h academico.cem.edu.ec -p 5432 -U attinae_user -C -f "C:\Users\Luis Cevallos\Documents\NetBeansProjects\academico\src\main\resources\sql\%nombre_archivo%.dump" attinae_db
rem pg_dump --no-owner --dbname=postgresql://username:password@host:5432/attinae_db
rem pg_dump -h academico.cem.edu.ec -p 5432 -U postgres -C -f C:\Users\Luis Cevallos\Documents\NetBeansProjects\academico\src\main\resources\sql\%nombre_archivo%.dump attinae_db
rem pg_dump -h 201.183.230.79 -p 5432 -U postgres -C -f C:\Users\Luis Cevallos\Documents\NetBeansProjects\academico\src\main\resources\sql\%nombre_archivo%.dump attinae_db

pause
echo Espero se haya creado el respaldo de la base de datos
pause
exit

