rem: GENERISANJE CRL LISTE I POMJERANJE POVUCENIH SERTIFIKATA 
openssl ca -revoke ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-medjusertifikat.pem -crl_reason superseded -keyfile ..\arch.sertifikacija.aktivno\server.privatni_kljuc.pem.key -cert ..\arch.sertifikacija.aktivno\server.pristupni_sertifikat.cer -config openssl.cnf -passin pass:server
openssl ca -gencrl -out ..\arch.sertifikacija.povuceno\sertifikacija.pem.crl -keyfile ..\arch.sertifikacija.aktivno\server.privatni_kljuc.pem.key -cert ..\arch.sertifikacija.aktivno\server.pristupni_sertifikat.cer -config openssl.cnf -passin pass:server
openssl crl -in ..\arch.sertifikacija.povuceno\sertifikacija.pem.crl -out ..\arch.sertifikacija.povuceno\sertifikacija.der.crl -inform PEM -outform DER
copy ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-medjusertifikat.pem ..\arch.sertifikacija.povuceno\petar-11.02.2018.21.00.59-PK-medjusertifikat.pem
copy ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-NK-zahtijev_sertifikata.csr ..\arch.sertifikacija.povuceno\petar-11.02.2018.21.00.59-NK-zahtijev_sertifikata.csr
copy ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-privatni_sertifikat.jks ..\arch.sertifikacija.povuceno\petar-11.02.2018.21.00.59-PK-privatni_sertifikat.jks
copy ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-medjusertifikat.p12 ..\arch.sertifikacija.povuceno\petar-11.02.2018.21.00.59-PK-medjusertifikat.p12
copy ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-privatni_kljuc.pem.key ..\arch.sertifikacija.povuceno\petar-11.02.2018.21.00.59-PK-privatni_kljuc.pem.key
copy ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-NPK-pristupni_sertifikat.der ..\arch.sertifikacija.povuceno\petar-11.02.2018.21.00.59-NPK-pristupni_sertifikat.der
copy ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-NPKS-pristupni_sertifikat.cer ..\arch.sertifikacija.povuceno\petar-11.02.2018.21.00.59-NPKS-pristupni_sertifikat.cer
del ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-medjusertifikat.pem
del ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-NK-zahtijev_sertifikata.csr 
del ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-privatni_sertifikat.jks
del ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-medjusertifikat.p12
del ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-PK-privatni_kljuc.pem.key
del ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-NPK-pristupni_sertifikat.der
del ..\arch.sertifikacija.aktivno\petar-11.02.2018.21.00.59-NPKS-pristupni_sertifikat.cer
pause

