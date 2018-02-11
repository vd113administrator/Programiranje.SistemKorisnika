#GENERISANJE POTPISANOG SERTIFIKATA KORISNIKA OD STRANE SERVERA
keytool -genkey -keystore <dir>/<mainstore> -alias <username> -keyalg RSA -keysize 2048 -keypass server -storepass server -dname "C=<c>, ST=<st>, L=<l>, O=<o>, OU=<ou>, CN=<cn>"
keytool -importkeystore -srckeystore <dir>/<mainstore> -destkeystore <dir>/<certname>-PK-medjusertifikat.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass server -deststorepass server -srcalias <username>
openssl pkcs12 -in <dir>/<certname>-PK-medjusertifikat.p12 -out <dir>/<certname>-PK-medjusertifikat.pem -passin pass:server -passout pass:server 
openssl x509 -outform der -in <dir>/<certname>-PK-medjusertifikat.pem -out <dir>/<certname>-NPK-pristupni_sertifikat.der
openssl pkcs12 -in <dir>/<certname>-PK-medjusertifikat.p12 -nocerts -out <dir>/<certname>-PK-privatni_kljuc.pem.key -passin pass:server -passout pass:server
keytool -importkeystore -srckeystore <dir>/<certname>-PK-medjusertifikat.p12 -srcstoretype pkcs12 -destkeystore <dir>/<certname>-PK-privatni_sertifikat.jks -keypass server -alias <username> -storepass server -srcstorepass server
keytool -keystore <dir>/<mainstore> -certreq -alias <username> -keyalg rsa -file <dir>/<certname>-NK-zahtijev_sertifikata.csr -keypass server -storepass server
openssl x509 -req -CA <dir>/server.medjusertifikat.pem -CAkey <dir>/server.privatni_kljuc.pem.key -in <dir>/<certname>-NK-zahtijev_sertifikata.csr -out <dir>/<certname>-NPKS-pristupni_sertifikat.cer  -days 365  -CAcreateserial -passin pass:server
read -n1 -r -p "Press any key to continue..." key 