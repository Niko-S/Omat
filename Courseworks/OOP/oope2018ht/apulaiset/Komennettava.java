package oope2018ht.apulaiset;

/**
  * Viestien testaamiseen soveltuvia metodeja, joista on todenn�k�isesti paljon
  * hy�ty� harjoisty�ss� my�s sellaisenaan. Kiinnit� tyyppimuuttujan V tyypiksi
  * Viesti, kun toteutat t�m�n rajapinnan Viesti-luokassa.
  * <p>
  * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  * <p>
  * @version 1.0.
  */

public interface Komennettava<V> {

   /** Tyhjennetyn viestin teksti (10 x miinusmerkki).
     */
   public static final String POISTETTUTEKSTI = "----------";

   /** Hakee viesti� listalta, joka s�il�� viitteet viestiin vastaaviin viesteihin.
     * Hy�dynt�� OmaLista-luokan hae-operaatiota.
     *
     * @param haettava viite haettavaan viestiin.
     * @return viite l�ydettyyn tietoon. Palauttaa null-arvon, jos haettavaa
     * viesti� ei l�ydet�.
     * @throws IllegalArgumentException jos parametri on null-arvoinen.
     */
   abstract public V hae(V haettava) throws IllegalArgumentException;
   
   /** Lis�� uuden viitteen listalle, joka s�il�� viitteet viestiin vastaaviin
     * viesteihin. Uusi viite lis�t��n listan loppuun. Viitett� ei lis�t�,
     * jos listalla on jo viite lis�tt�v��n vastaukseen. Hy�dynt�� hae-metodia.
     *
     * @param lisattava viite lis�tt�v��n viestiin.
     * @throws IllegalArgumentException jos lis�ys ep�onnistui, koska parametri
     * on null-arvoinen tai koska vastaus on jo lis�tty listalle.
     */
   abstract public void lisaaVastaus(V lisattava) throws IllegalArgumentException;

   /** Asettaa viestin tekstiksi vakion POISTETTUTEKSTI ja poistaa viestiin
     * mahdollisesti liitetyn tiedoston asettamalla attribuutin arvoksi null-arvon.
     */
   abstract public void tyhjenna();
}
