package model;

public enum Color {
    UNKNOWN(""),
    RED("#EF161E"),
    GREEN("#2DBE2C"),
    BLUE("#0078BE"),
    LIGHT_BLUE("#00BFFF"),
    BROWN("#8D5B2D"),
    ORANGE("#ED9121"),
    PURPLE("#800080"),
    YELLOW("#FFD702"),
    GREY("#999999"),
    LIME("#99CC00"),
    TURQUOISE("#82C0C0"),
    YELLOW_BLUE("#FFD702,#82C0C0"),
    MEDIUM_SALTE_BLUE("#A1B3D4"),
    PINK("#DE64A1"),
    MEDIUM_PURPLE("#9999FF"),
    WHITE("#FFFFFF");

    private String code;
    Color(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }
}
