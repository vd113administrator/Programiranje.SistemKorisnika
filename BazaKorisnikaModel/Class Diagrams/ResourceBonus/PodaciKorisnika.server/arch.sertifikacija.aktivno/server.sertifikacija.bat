rem: GENERISANJE SAMO POTPISANOG SERTIFIKATA SERVERA 
keytool -genkey -keystore podaci_korisnika.jmks -alias server -keyalg RSA -keysize 2048 -keypass server -storepass server -dname "C=BA, ST=Republika Srpska, L=Banja Luka, O=Elektrotehnicki fakultet Univerziteta u Banjoj Luci OU=Razvoj i zastita softvera\, studiranje, CN=Mirko Vidakovic"
keytool -importkeystore -srckeystore podaci_korisnika.jmks -destkeystore server.medjusertifikat.p12 -srcstoretype JKS -deststoretype PKCS12 -srcstorepass server -deststorepass server -srcalias server
openssl pkcs12 -in server.medjusertifikat.p12 -out server.medjusertifikat.pem -passin pass:server -passout pass:server 
openssl x509 -outform der -in server.medjusertifikat.pem -out server.pristupni_sertifikat.der
openssl pkcs12 -in server.medjusertifikat.p12 -nocerts -out server.privatni_kljuc.pem.key -passin pass:server -passout pass:server
keytool -importkeystore -srckeystore server.medjusertifikat.p12 -srcstoretype pkcs12 -destkeystore server.privatni_sertifikat.jks -keypass server -alias server -storepass server -srcstorepass server
keytool -keystore podaci_korisnika.jmks -certreq -alias server -keyalg rsa -file server.zahtijev_sertifikata.csr -keypass server -storepass server
openssl x509 -req -CA server.medjusertifikat.pem -CAkey server.privatni_kljuc.pem.key -in server.zahtijev_sertifikata.csr -out server.pristupni_sertifikat.cer  -days 365  -CAcreateserial -passin pass:server
pause 

