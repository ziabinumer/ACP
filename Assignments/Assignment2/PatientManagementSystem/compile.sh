#!/bin/bash

# Compile Java files with SQLite driver
javac -cp "lib/sqlite-jdbc-3.51.0.0.jar:bin" -d bin \
src/main/TestDAO.java \
src/main/Main.java \
src/enums/Sex.java \
src/dao/interfaces/PatientDAO.java \
src/dao/interfaces/DoctorDAO.java \
src/dao/interfaces/DiseaseDAO.java \
src/dao/impl/DiseaseDAOImpl.java \
src/dao/impl/DoctorDAOImpl.java \
src/dao/impl/PatientDAOImpl.java \
src/database/DatabaseConnection.java \
src/database/DatabaseManager.java \
src/models/Disease.java \
src/models/Doctor.java \
src/models/Patient.java \
src/enums/Sex.java \
src/logging/AppLogger.java

echo "Compilation done."
