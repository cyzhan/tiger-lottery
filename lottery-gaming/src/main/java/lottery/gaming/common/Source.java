package lottery.gaming.common;

public enum Source {

    A("bet188"), B("betradar"), C("betgenius");

    final private String value;

    private Source(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }

}
