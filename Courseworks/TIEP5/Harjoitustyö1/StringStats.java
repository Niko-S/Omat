/*
 * Niko Sainio (sainio.niko.l@student.uta.fi)
 * Harjoitustyö 1
 * Tämä ohjelma poistaa merkkijonosta merkit ja laskee siitä eri tunnuslukuja
 */
 
public class StringStats{
    public static void main(String[] args){

        System.out.println ("Hello! I calculate some string statistics.");

        // Tämä while mahdollistaa uuden syötteen antamisen ilman ohjelmasta poistumista
        boolean jatka = true;
        while(jatka == true){

            // Pyydetään käyttäjältä merkkijono ja alustetaan tarvittavat muuttujat
            System.out.println ("Please, enter a string:");
            String alkujono = In.readString();
            
            int alkupituus = alkujono.length();
            String loppujono = "";
            final char Y = 'y';
            final char N = 'n';
            int min1 = 1000;
            int min2 = 1000;
            int max1 = 1;
            int max2 = 1;
            int sanaluku = 0;
            int merkityht = 0;
            final char VALI = ' ';

            // Jos merkkijonon pituus on 1-1000 merkkiä niin edetään ohjelmassa
            if (alkupituus > 0 && alkupituus <= 1000){
                // Silmukka käy jokaisen merkin läpi ja poistaa merkkijonosta myöhemmin mainitut merkit
                for (int i = 0; i < alkupituus; i++){
                    char merkki = alkujono.charAt(i);
                    if (merkki == '.');
                    else if (merkki == ',');
                    else if (merkki == ':');
                    else if (merkki == ';');
                    else if (merkki == '\'');
                    else if (merkki == '\"');
                    else if (merkki == '?');
                    else if (merkki == '!');
                    else if (merkki == '(');
                    else if (merkki == ')');
                    else if (merkki == '/');
                    else loppujono = loppujono + merkki;
                }

                // Uusi for-lauseke jossa käydään käsitelty merkkijono läpi
                int loppupituus = loppujono.length();
                int sanapituus = 0;
                for (int x = 0; x < loppupituus; x++){
                    char merkki = loppujono.charAt(x);
                    // Selvitetään välilyöntien erottelemien merkkiryhmien tunnuslukuja
                    if (merkki == VALI || x >= (loppupituus - 1)){
                        // Tämän if-lausekkeen avulla saadaan viimeinenkin merkki pituuslaskuriin
                        if (x >= (loppupituus - 1)) sanapituus = sanapituus + 1;
                        
                        sanaluku = sanaluku + 1;
                        merkityht = merkityht + sanapituus;

                        // Vertailaan ja tallennetaan muuttujiin kaksi pienintä ja suurinta arvoa
                        // Tämä if ratkaisee ongelman jossa 2 ekaa merkkiryhmää yhtä pitkiä(1)
                        if (sanaluku == 2 && max1 == sanapituus) max2 = sanapituus;
                        // Tämä if ratkaisee ongelman jossa vain kahta eripituista merkkiryhmää(2)
                        else if (max1 == max2 && sanapituus < max2) max2 = sanapituus;
                        else if (sanapituus > max1 && sanapituus > max2){
                            max2 = max1;
                            max1 = sanapituus;
                        }
                        else if (sanapituus > max2 && sanapituus < max1) max2 = sanapituus;
                       
                        // 1
                        if (sanaluku == 2 && min1 == sanapituus) min2 = sanapituus;
                        // 2
                        else if (min1 == min2 && sanapituus > min2) min2 = sanapituus;
                        else if (sanapituus < min1 && sanapituus < min2){
                            min2 = min1;
                            min1 = sanapituus;
                        }
                        else if (sanapituus < min2 && sanapituus > min1) min2 = sanapituus;
                       
                       // Resetoi laskurin välilyönnin kohdalla, mutta sallii tiedon säilymisen merkkijonon päätyttyä
                        if (x != (loppupituus - 1)) sanapituus = 0;
                    }
                    // Laskuri joka mittaa merkkiryhmän pituuden
                    else sanapituus = sanapituus + 1;
                }
                    // Jos merkkiryhmien määrä 1 niin molemmat pienimmät ja suurimmat sama.
                    // (Voisi kai olla for-silmukassa, mutta ei uskalla koskea ettei mene särki)
                    if (sanaluku == 1){
                        max2 = max1;
                        min2 = min1;
                    }

                // Tulostetaan siivottu merkkijono ja tunnusluvut.
                double merkitka = (double)merkityht / sanaluku;
                System.out.println ('"' + loppujono + '"');
                System.out.println ("- The number of parts is " + sanaluku + '.');
                System.out.println ("- The sum of part lengths is " + merkityht + '.');
                System.out.println ("- The average length of parts is " + Math.round(merkitka) + '.');
                System.out.println ("- The length of the shortest part is " + min1 + '.');
                System.out.println ("- The length of the second shortest part is " + min2 + '.');
                System.out.println ("- The length of the second longest part is " + max2 + '.');
                System.out.println ("- The length of the longest part is " + max1 + '.');

                // Kysytään käyttäjältä haluaako jatkaa. Jos ei, poistutaan ohjelmasta.
                // Merkkiä kysytään niin kauan kunnes saadaan oikea vastaus.
                boolean kysymys = true;
                while (kysymys == true){
                    System.out.println ("Continue (y/n)?");
                    char input = In.readChar();
                    if (input == Y){
                        kysymys = false;
                    }
                    else if (input == N){
                        jatka = false;
                        kysymys = false;
                        System.out.println ("See you soon.");
                    }
                    else System.out.println ("Error!");
                }
            }
        }
    }
}