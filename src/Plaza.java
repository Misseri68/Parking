public class Plaza {
    private boolean ocupada;
    private int numPlaza;

    public Plaza(int numPlaza, boolean ocupada) {
        this.ocupada = ocupada;
        this.numPlaza = numPlaza;
    }

    public Plaza (int numPlaza){
        this.numPlaza = -1;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public int getNumPlaza() {
        return numPlaza;
    }

}
