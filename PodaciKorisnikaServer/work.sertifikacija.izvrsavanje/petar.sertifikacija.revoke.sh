# GENERISANJE CRL LISTE I POMJERANJE POVUCENIH SERTIFIKATA 
openssl ca -revoke ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-medjusertifikat.pem -crl_reason superseded -keyfile ../arch.sertifikacija.aktivno/server.privatni_kljuc.pem.key -cert ../arch.sertifikacija.aktivno/server.pristupni_sertifikat.cer -config openssl.cnf -passin pass:server
openssl ca -gencrl -out ../arch.sertifikacija.povuceno/sertifikacija.pem.crl -keyfile ../arch.sertifikacija.aktivno/server.privatni_kljuc.pem.key -cert ../arch.sertifikacija.aktivno/server.pristupni_sertifikat.cer -config openssl.cnf -passin pass:server
openssl crl -in ../arch.sertifikacija.povuceno/sertifikacija.pem.crl -out ../arch.sertifikacija.povuceno/sertifikacija.der.crl -inform PEM -outform DER
cp ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-medjusertifikat.pem ../arch.sertifikacija.povuceno/petar-11.02.2018.21.00.59-PK-medjusertifikat.pem
cp ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-NK-zahtijev_sertifikata.csr ../arch.sertifikacija.povuceno/petar-11.02.2018.21.00.59-NK-zahtijev_sertifikata.csr
cp ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-privatni_sertifikat.jks ../arch.sertifikacija.povuceno/petar-11.02.2018.21.00.59-PK-privatni_sertifikat.jks
cp ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-medjusertifikat.p12 ../arch.sertifikacija.povuceno/petar-11.02.2018.21.00.59-PK-medjusertifikat.p12
cp ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-privatni_kljuc.pem.key ../arch.sertifikacija.povuceno/petar-11.02.2018.21.00.59-PK-privatni_kljuc.pem.key
cp ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-NPK-pristupni_sertifikat.der ../arch.sertifikacija.povuceno/petar-11.02.2018.21.00.59-NPK-pristupni_sertifikat.der
cp ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-NPKS-pristupni_sertifikat.cer ../arch.sertifikacija.povuceno/petar-11.02.2018.21.00.59-NPKS-pristupni_sertifikat.cer
rm ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-medjusertifikat.pem
rm ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-NK-zahtijev_sertifikata.csr 
rm ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-privatni_sertifikat.jks
rm ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-medjusertifikat.p12
rm ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-PK-privatni_kljuc.pem.key
rm ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-NPK-pristupni_sertifikat.der
rm ../arch.sertifikacija.aktivno/petar-11.02.2018.21.00.59-NPKS-pristupni_sertifikat.cer
read -n1 -r -p "Press any key to continue..." key 

