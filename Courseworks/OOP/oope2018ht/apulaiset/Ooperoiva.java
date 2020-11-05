package oope2018ht.apulaiset;

/**
  * Pakolliset uudet listaoperaatiot m��rittelev� geneerinen rajapinta. Kiinnit�
  * tyyppimuuttuja T tyypiksi OmaLista, kun toteutat t�m�n rajapinnan OmaLista
  * -luokassa.
  * <p>
  * Harjoitusty�, Olio-ohjelmoinnin perusteet, kev�t 2018.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi), Luonnontieteiden tiedekunta,
  * Tampereen yliopisto.
  * <p>
  * @version 1.0.
  */

public interface Ooperoiva<T> {

   /** Tutkii onko listalla haettavaa oliota equals-mieless� vastaava alkio.     
     * Haku aloitetaan listan alusta ja se etenee kohti listan loppua.
     * Haku pys�htyy ensimm�iseen osumaan, jos listalla on useita haettavaa oliota
     * vastaavia alkioita. Operaatio ei muuta listan sis�lt�� mill��n tavalla.
     *
     * Jos parametri liittyy esimerkiksi merkkijonoon "ab" ja listan tietoalkiot
     * ovat [ "ab", "AB, "Ab", "aB", "ab" ], palauttaa operaatio viitteen listan
     * ensimm�iseen tietoalkioon, koska "ab".equals("ab") == true ja kysess� on
     * haun ensimm�inen osuma.
     *
     * @param haettava olio, jota vastaavaa alkiota listalta haetaan. Olion luokan
     * tai luokan esivanhemman oletetaan korvanneen Object-luokan equals-metodin.
     * @return viite l�ydettyyn alkioon. Paluuarvo on null, jos parametri on
     * null-arvoinen, vastaavaa alkiota ei l�ydy tai lista on tyhj�.
     */
   public abstract Object hae(Object haettava);
   
   /** Listan alkioiden v�lille muodostuu kasvava suuruusj�rjestys, jos lis�ys
     * tehd��n vain t�ll� operaatiolla, koska uusi alkion lis�t��n listalle siten,
     * ett� alkio sijoittuu kaikkien itse��n pienempien tai yht� suurien alkioiden
     * j�lkeen ja ennen kaikkia itse��n suurempia alkioita. Alkioiden suuruus-
     * j�rjestys selvitet��n Comparable-rajapinnan compareTo-metodilla.
     *
     * Jos parametri liittyy esimerkiksi kokonaislukuun 2 ja listan tietoalkiot
     * ovat [ 0, 3 ], on listan sis�lt� lis�yksen j�lkeen [ 0, 2, 3 ],
     * koska 
     *
     * Sijoita toteuttamasi metodin yleisten kommenttien ja metodin otsikon v�liin
     * annotaatio "@SuppressWarnings({"unchecked"})", jolla k��nt�j�lle vakuutetaan,
     * ett� metodin koodi on turvallista. Ilman m��rett� k��nt�j� varoittaa,
     * ett� Comparable-rajapintaa k�ytet��n ei-geneerisell� tavalla. Esimerkki
     * annotaation k�yt�st� on annettu harjoitusteht�v�n 7.3. mallivastauksessa.
     *
     * @param uusi viite olioon, jonka luokan tai luokan esivanhemman oletetaan
     * toteuttaneen Comparable-rajapinnan.
     * @return true, jos lis�ys onnistui ja false, jos uutta alkiota ei voitu
     * vertailla. Vertailu ep�onnistuu, kun parametri on null-arvoinen tai sill�
     * ei ole vastoin oletuksia Comparable-rajapinnan toteutusta.
     */
   public abstract boolean lisaa(Object uusi);
   
   /** Luo ja palauttaa uuden listan, jolla on viitteet n ensimm�iseen listan
     * tietoalkioon. Palautettavan listan ensimm�inen viite liittyy listan
     * ensimm�iseen alkioon, palautettavan listan toinen viite liittyy listan
     * toiseen alkioon ja niin edelleen. Palautettavan listan viimeinen viite
     * liittyy listan n. alkioon. Operaatio ei muuta listan sis�lt�� mill��n
     * tavalla.
     *
     * Jos parametrin arvo on esimerkiksi kaksi ja listan tietoalkiot ovat
     * [ "AB", "Ab", "aB", "ab" ], on palautetulta listalta viitteet listan
     * ensimm�iseen ja toiseen alkioon, jolloin palautetun listan tietoalkiot
     * ovat [ "AB, "Ab" ].
     *
     * @param n palautettavalle listalle lis�tt�vien viitteiden lukum��r�.
     * @return viite listaan, joka sis�lt�� viitteet listan n ensimm�iseen
     * tietoalkioon. Paluuarvo on null, jos lista on tyhj� tai jos parametrin
     * arvo on pienempi kuin yksi tai suurempi kuin listan alkioiden lukum��r�.
     */
   public abstract T annaAlku(int n);

   /** Luo ja palauttaa uuden listan, jolla on viitteet n viimeiseen listan
     * tietoalkioon. Palautettavan listan ensimm�inen viite liittyy listan m.
     * (koko - n + 1) alkioon, palautettavan listan toinen viite liittyy listan
     * m + 1. (koko - n + 2) alkioon ja niin edelleen. Palautettavan listan
     * viimeinen viite liittyy listan viimeiseen alkioon. Operaatio ei muuta
     * listan sis�lt�� mill��n tavalla.
     *
     * Jos parametrin arvo on esimerkiksi kaksi ja listan tietoalkiot ovat
     * [ "AB", "Ab", "aB", "ab" ], on palautetulta listalta viitteet listan
     * kolmanteen ja nelj�nteen alkioon, jolloin palautetun listan tietoalkiot
     * ovat [ "aB, "ab" ].
     *
     * @param n palautettavalle listalle lis�tt�vien viitteiden lukum��r�.
     * @return viite listaan, joka sis�lt�� viitteet listan n viimeiseen
     * tietoalkioon. Paluuarvo on null, jos lista on tyhj� tai jos parametrin
     * arvo on pienempi kuin yksi tai suurempi kuin listan alkioiden lukum��r�.
     */
   public abstract T annaLoppu(int n);
}
