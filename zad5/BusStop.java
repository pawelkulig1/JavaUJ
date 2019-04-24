class BusStop implements BusStopInterface {
    private String name;
    
    public BusStop(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }

    public String toString(){
        return "BusStop: " + name;
    }

}
