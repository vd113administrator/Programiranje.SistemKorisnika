# GENERISANJE CRL LISTE I POMJERANJE POVUCENIH SERTIFIKATA 
openssl ca -revoke <dir>/<certname>-PK-medjusertifikat.pem -crl_reason superseded -keyfile <dir>/server.privatni_kljuc.pem.key -cert <dir>/server.pristupni_sertifikat.cer -config openssl.cnf -passin pass:server
openssl ca -gencrl -out <revokedir>/sertifikacija.pem.crl -keyfile <dir>/server.privatni_kljuc.pem.key -cert <dir>/server.pristupni_sertifikat.cer -config openssl.cnf -passin pass:server
openssl crl -in <revokedir>/sertifikacija.pem.crl -out <revokedir>/sertifikacija.der.crl -inform PEM -outform DER
cp <dir>/<certname>-PK-medjusertifikat.pem <revokedir>/<certname>-PK-medjusertifikat.pem
cp <dir>/<certname>-NK-zahtijev_sertifikata.csr <revokedir>/<certname>-NK-zahtijev_sertifikata.csr
cp <dir>/<certname>-PK-privatni_sertifikat.jks <revokedir>/<certname>-PK-privatni_sertifikat.jks
cp <dir>/<certname>-PK-medjusertifikat.p12 <revokedir>/<certname>-PK-medjusertifikat.p12
cp <dir>/<certname>-PK-privatni_kljuc.pem.key <revokedir>/<certname>-PK-privatni_kljuc.pem.key
cp <dir>/<certname>-NPK-pristupni_sertifikat.der <revokedir>/<certname>-NPK-pristupni_sertifikat.der
cp <dir>/<certname>-NPKS-pristupni_sertifikat.cer <revokedir>/<certname>-NPKS-pristupni_sertifikat.cer
rm <dir>/<certname>-PK-medjusertifikat.pem
rm <dir>/<certname>-NK-zahtijev_sertifikata.csr 
rm <dir>/<certname>-PK-privatni_sertifikat.jks
rm <dir>/<certname>-PK-medjusertifikat.p12
rm <dir>/<certname>-PK-privatni_kljuc.pem.key
rm <dir>/<certname>-NPK-pristupni_sertifikat.der
rm <dir>/<certname>-NPKS-pristupni_sertifikat.cer
read -n1 -r -p "Press any key to continue..." key 