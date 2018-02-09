rem: GENERISANJE POTPISANOG SERTIFIKATA KORISNIKA OD STRANE SERVERA
keytool -genkey -keystore ..\arch.sertifikacija.aktivno\podaci_korisnika.jmks -alias kojo -keyalg RSA -keysize 2048 -keypass server -storepass server -dname "C=BA, ST=Republika Srpska, L=, O=, OU=, CN=Kojo Kojic"
keytool -importkeystore -srckeystore ..\arch.sertifikacija.aktivno\podaci_korisnika.jmks -destkeystore ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-medjusertifikat.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass server -deststorepass server -srcalias kojo
openssl pkcs12 -in ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-medjusertifikat.p12 -out ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-medjusertifikat.pem -passin pass:server -passout pass:server 
openssl x509 -outform der -in ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-medjusertifikat.pem -out ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-NPK-pristupni_sertifikat.der
openssl pkcs12 -in ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-medjusertifikat.p12 -nocerts -out ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-privatni_kljuc.pem.key -passin pass:server -passout pass:server
keytool -importkeystore -srckeystore ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-medjusertifikat.p12 -srcstoretype pkcs12 -destkeystore ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-PK-privatni_sertifikat.jks -keypass server -alias kojo -storepass server -srcstorepass server
keytool -keystore ..\arch.sertifikacija.aktivno\podaci_korisnika.jmks -certreq -alias kojo -keyalg rsa -file ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-NK-zahtijev_sertifikata.csr -keypass server -storepass server
openssl x509 -req -CA ..\arch.sertifikacija.aktivno\server.medjusertifikat.pem -CAkey ..\arch.sertifikacija.aktivno\server.privatni_kljuc.pem.key -in ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-NK-zahtijev_sertifikata.csr -out ..\arch.sertifikacija.aktivno\kojo-31.01.2018.20.31.31-NPKS-pristupni_sertifikat.cer  -days 365  -CAcreateserial -passin pass:server
pause

