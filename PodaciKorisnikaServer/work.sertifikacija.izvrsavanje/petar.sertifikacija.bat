rem: GENERISANJE POTPISANOG SERTIFIKATA KORISNIKA OD STRANE SERVERA
keytool -genkey -keystore ..\arch.sertifikacija.aktivno\podaci_korisnika.jmks -alias petar -keyalg RSA -keysize 2048 -keypass server -storepass server -dname "C=BA, ST=Republika Srpska, L=, O=, OU=, CN=Petar Petrovic"
keytool -importkeystore -srckeystore ..\arch.sertifikacija.aktivno\podaci_korisnika.jmks -destkeystore ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-medjusertifikat.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass server -deststorepass server -srcalias petar
openssl pkcs12 -in ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-medjusertifikat.p12 -out ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-medjusertifikat.pem -passin pass:server -passout pass:server 
openssl x509 -outform der -in ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-medjusertifikat.pem -out ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-NPK-pristupni_sertifikat.der
openssl pkcs12 -in ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-medjusertifikat.p12 -nocerts -out ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-privatni_kljuc.pem.key -passin pass:server -passout pass:server
keytool -importkeystore -srckeystore ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-medjusertifikat.p12 -srcstoretype pkcs12 -destkeystore ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-PK-privatni_sertifikat.jks -keypass server -alias petar -storepass server -srcstorepass server
keytool -keystore ..\arch.sertifikacija.aktivno\podaci_korisnika.jmks -certreq -alias petar -keyalg rsa -file ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-NK-zahtijev_sertifikata.csr -keypass server -storepass server
openssl x509 -req -CA ..\arch.sertifikacija.aktivno\server.medjusertifikat.pem -CAkey ..\arch.sertifikacija.aktivno\server.privatni_kljuc.pem.key -in ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-NK-zahtijev_sertifikata.csr -out ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.02.40-NPKS-pristupni_sertifikat.cer  -days 365  -CAcreateserial -passin pass:server
pause

