## Kako podesiti HTTPS?

1. Potrebno je eksportovati sertifikate iz  keystore-a Agenta i Centra. Za ovo je najlakše koristiti keytool.
   
   `keytool -exportcert -rfc -alias agentkey -keystore keystore.jks -file agent.crt`
   
   `keytool -exportcert -rfc -alias centerkey -keystore keystore.jks -file center.crt`

2. Zatim treba locirati Java truststore pod nazivom cacerts

3. Importovati sertifikate u `cacerts`. Ovo je najjednostavnije odraditi pomoću alata `portecle`. 
    
    1. Šifra za otvaranje cacerts-a je `changeit`
    
    2. Import trusted certificate
    
    3. Potvrdite da se sertifikatu može verovati i dodelite mu naziv
    
    4. **Save keystore**

4. Pokrenuti aplikacije
