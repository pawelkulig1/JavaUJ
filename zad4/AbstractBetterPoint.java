public abstract class AbstractBetterPoint {

    /**
     * Metoda pozwala na ustalenie liczby wymiarĂłw, ktĂłrÄ punkt bÄdzie obsĹugiwaĹ.
     * Poprawne wartoĹci dla identyfikatora wymiaru to od 0 do dimensions-1
     * wĹÄcznie.
     * 
     * @param dimensions liczba wymiarĂłw
     */
    public abstract void setDimensions(int dimensions);

    /**
     * Metoda zwraca aktualny poziom naĹoĹźonej ochrony. WartoĹci wiÄksze od zero
     * pokazujÄ ile poziomĂłw ochrony naĹoĹźono. WartoĹÄ zero oznacza, Ĺźe ochrony nie
     * ma i zmiana stanu jest moĹźliwa.
     * 
     * @return aktualny poziom ochrony przez modyfikacjÄ. Zero oznacza brak
     *         ograniczeĹ na modyfikowanie stanu obiektu.
     */
    public abstract int lockLevel();

    /**
     * Metoda blokuje modyfikacje obiektu do czasu wykonania metody unlock z
     * poprawnym hasĹem, to jest tym, jakie zostaĹo podane w wywoĹaniu metody lock.
     * Wielokrotne wykonanie metody lock powoduje naĹoĹźenie kolejnego poziomu
     * ochrony i wprowadzenie hasĹa kolejnego poziomu. JeĹli np. 3 razy wykonano
     * metodÄ lock podajÄc hasĹa h1, h2 i h3, to aby zdjÄÄ ochronÄ naleĹźy 3 razy
     * wykonaÄ metodÄ unlock podajÄc kolejno hasĹa h3, h2 i h1.
     *
     * @param password hasĹo jakie naleĹźy podaÄ aby odblokowaÄ moĹźliwoĹÄ zmian stanu
     *                 obiektu na danym poziomie ochrony.
     * @return poziom ochrony - pierwsze wykonanie lock powinno zwrĂłciÄ 1, kolejne
     *         (o ile nie wykonano unlock z poprawnym hasĹem) powinno zwrĂłciÄ 2 itd.
     */
    public abstract int lock(String password);

    /**
     * W przypadku podania poprawnego hasĹa, metoda zdejmuje jeden poziom blokady
     * moĹźliwoĹÄ zmiany stanu obiektu. PoniewaĹź istnieje moĹźliwoĹÄ wielopoziomowego
     * zabezpiecznia modyfikacji odblokowanie moĹźliwoĹci zmiany obiektu moze siÄ
     * wiÄzaÄ z wielokrotnym wykonaniem metody unlock. Zmiany sÄ moĹźliwe tylko
     * wtedy, gdy aktualny poziom ochrony wynosi 0 tj. popranie wykonano odpowiedniÄ
     * liczbÄ wywoĹaĹ unlock.
     *
     * @param password hasĹo, ktĂłre ma odblokowaÄ dostÄp do modyfikacji obiektu na
     *                 danym poziomie.
     * @return liczba poziomĂłw ochrony, ktĂłrÄ naleĹźy usunÄÄ, aby modyfikacje byĹy
     *         moĹźliwe. 0 - modyfikacje obiektu sÄ moĹźliwe - obiekt nie jest
     *         chroniony. Wynik wiÄkszy od 0 oznacza, Ĺźe naleĹźy ochrona obiektu jest
     *         nadal aktywna.
     */
    public abstract int unlock(String password);

    /**
     * Metoda pozwala na przemieszczenie podanego wymiaru punktu o delta.
     * Modyfikacje poĹoĹźenia jest moĹźliwa tylko gdy modyfikacje obiektu nie zostaĹy
     * wczeĹniej wstrzymane za pomocÄ metody lock. Nowa wartoĹÄ wspĂłĹrzÄdnej dla
     * danego wymiaru to suma wartoĹci poprzedniej oraz delta.
     * 
     * @param dimension wymiar, ktĂłry ma ulec zmianie
     * @param delta     wielkoĹÄ, o ktĂłrÄ wspĂłĹrzÄdna ma zostaÄ zmodyfikowana
     * @return true jeĹli doszĹo do zmiany poĹoĹźenia, false gdy zmiana nie nastÄpiĹa
     *         z powodu blokady moĹźliwoĹci zmiany lub gdy delta byĹa rĂłwna zero.
     */
    public abstract boolean move(int dimension, double delta);

    /**
     * Metoda pozwala na przypisanie okreĹlonej wartoĹci do wybranej wspĂłĹrzÄdnej
     * punktu. Przypisanie wartoĹci moĹźliwe jest tylko, gdy modyfikacje obiektu nie
     * sÄ zablokowane.
     * 
     * @param dimension wymiar, do ktĂłrego naleĹźy wpisaÄ value
     * @param value     nowa wartoĹÄ wspĂłĹrzÄdnej dla wymiaru dimension
     * @return true - poprawnie wpisano wartoĹÄ wspĂłĹrzÄdnej, false - modyfikacje
     *         zablokowane.
     */
    public abstract boolean set(int dimension, double value);

    /**
     * Metoda zwraca okreĹlonÄ wspĂłĹrzÄdnÄ punktu.
     * 
     * @param dimension wymiar, ktĂłry ma zostaÄ zwrĂłcony w wyniku
     * @return wartoĹÄ podanej wspĂłĹrzÄdnej
     */
    public abstract double get(int dimension);
}
