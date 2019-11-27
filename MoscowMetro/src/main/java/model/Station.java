package model;

public class Station implements Comparable<Station>{
    private Line line;
    private String nameStation;

    public Station(Line line, String nameStation) {
        this.line = line;
        this.nameStation = nameStation;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getNameStation() {
        return nameStation;
    }

    public void setNameStation(String nameStation) {
        this.nameStation = nameStation;
    }

    @Override
    public int compareTo(Station o) {
        int lineComparison = line.compareTo(o.getLine());
        if(lineComparison != 0) {
            return lineComparison;
        }
        return nameStation.compareTo(o.getNameStation());
    }

//    @Override
//    public String toString() {
//        return "Station{" +
//                "line= " + line.getNumberLine() +
//                " : " + line.getNameLine() +
//                ", nameStation= '" + nameStation + '\'' +
//                '}';
//    }
    @Override
    public String toString() {
        return  "\"" + nameStation + "\"";
    }
}
