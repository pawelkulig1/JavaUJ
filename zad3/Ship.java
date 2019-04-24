import java.util.*;

/**
 * Klasa reprezentujÄca okrÄt o okreĹlonym rozmiarze. Obiekty posiadajÄ pole
 * shipwreck, ktĂłre pozwala okreĹliÄ stan w jakim okrÄt siÄ znajduje.
 */
public class Ship {
    private boolean shipwreck;
    private int size;
    private static ShipSizeLimit limit;
    
    private static ArrayList<Ship> allShipsArray = new ArrayList<Ship>();


    private Ship(int size){
        this.size = size;
    }

    /**
     * Metoda pozwala na ustalenie limitu iloĹci jednostek. WywoĹanie metody
     * setRules powoduje rozpoczÄcie generowanie obiektĂłw Ship od stanu
     * oznaczajÄcego brak okrÄtĂłw.
     *
     * @param limit obiekt klasy ShipSizeLimit zawierajÄcy limity liczby okrÄtĂłw.
     */
    public static void setLimit(ShipSizeLimit limit) {
        Ship.limit = limit;
        allShipsArray.clear();
    }

    /**
     * Metoda zwraca okreĹlonÄ liczbÄ okrÄtĂłw danego rozmiaru. Metoda nie pozwala na
     * posiadanie przez uĹźytkownika wiÄkszej liczby sprawnych obiektĂłw Ship niĹź
     * wynosi limit. Przekroczenie liczby okrÄtĂłw danego rozmiaru powoduje, Ĺźe
     * metoda getShip zwraca null. Limit liczby okrÄtĂłw ustalany jest za
     * poĹrednictwem obiektu klasy ShipSizeLimit. WywoĹanie metody przed ustaleniem
     * limitu poprzez setLimit powoduje, Ĺźe metoda zawsze zwraca null. WywoĹanie
     * metody dla wielkoĹci size, ktĂłra nie posiada limitu zawsze koĹczy siÄ
     * zwrĂłceniem null.
     * 
     * @param size rozmiar okrÄtu, ktĂłry chce otrzymaÄ uĹźytkownik
     * @return Metoda zwraca null gdy zostanie wywoĹana przed ustaleniem limitu
     *         okrÄtĂłw za pomocÄ sertLimit lub, gdy wyprodukowanie nowego
     *         obiektu-okrÄtu spowoduje przekroczenie limitu. Metoda zwraca obiekt
     *         reprezentujÄcy okrÄt o podanym rozmiarze, gdy limit wytworzonych
     *         okrÄtĂłw nie jest przekroczony, oraz, gdy liczba sprawnych okrÄtĂłw o
     *         danym rozmiarze jest mniejsza od limitu.
     */
    public static Ship getShip(int size) {
        if(size <= 0 || size > limit.getNumberOfSizes())
            return null; // to jest oczywiĹcie do wymiany

        int counter = 0;
        for(Ship ship:allShipsArray)
        {
            if(ship.size == size && !ship.isShipwreck())
                counter++;
        }

        if(counter >= limit.getLimit(size))
        {
            return null;
        }

        Ship tempShip = new Ship(size);
        allShipsArray.add(tempShip);
        return tempShip;
    }

    public void shipwreck() {
        shipwreck = true;
    }

    public boolean isShipwreck() {
        return shipwreck;
    }
}
