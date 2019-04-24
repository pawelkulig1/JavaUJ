/**
 * Klasa reprezentuje limit iloĹci okrÄtĂłw o danym rozmiarze. Po ustaleniu
 * limitu nie ma moĹźliwoĹcie jego zmiany - limit ustalany jest na etapie
 * tworzenia obiektu za pomocÄ konstruktora.
 */
public final class ShipSizeLimit {
    private final int[] shipSizes;

    /**
     * Konstruktor, do ktĂłrego przekazywana jest tablica zawierajÄca informacje o
     * limicie iloĹci jednostek danego rozmiaru.
     * 
     * @param shipSizes tablica z limitami iloĹci jednostek. Z uwagia na brak
     *                  okrÄtĂłw o rozmiarze 0, na pozycji shipSizes[x] znajduje siÄ
     *                  limit dla okrÄtĂłw rozmiaru x+1.
     */
    public ShipSizeLimit(int[] shipSizes) {
        this.shipSizes = new int[shipSizes.length];
        // generujemy kopiÄ przekazanej tablicy,
        // dziÄki temu wartoĹci nie bÄdzie moĹźna zmieniÄ.
        System.arraycopy(shipSizes, 0, this.shipSizes, 0, shipSizes.length);
    }

    /**
     * Liczba rĂłĹźnych rozmiarĂłw okrÄtĂłw. Wynik zwrĂłcony za pomocÄ getNumberOfSizes
     * to jednoczeĹnie rozmiar najwiÄkszej jednostki.
     *
     * @return liczba rozmiarĂłw okrÄtĂłw.
     */
    final public int getNumberOfSizes() {
        return shipSizes.length;
    }

    /**
     * Maksymalna liczba okrÄtĂłw o size rozmiarze. Dla size=0 metoda zawsze zwraca
     * 0. PrawidĹowe rozmiary okrÄtĂłw mieszczÄ siÄ w przedziale od 1 do
     * getNumberOfSizes.
     * 
     * @param size rozmiar okrÄtu
     * @return limit iloĹci jednostek o danym rozmiarze.
     */
    final public int getLimit(int size) {
        if (size == 0)
            return 0;
        return shipSizes[size - 1];
    }
}
