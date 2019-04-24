public class Bus implements BusInterface{
    private int busNumber;
    public Bus(int number){
        busNumber = number;
    }
    
    @Override
    public int getBusNumber() {
        return busNumber;
    }

    public String toString(){
        return "bus: " + busNumber;
    }

}
