package model;

import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line>{
    private String numberLine;
    private String nameLine;
    private Color colorLine;
    private List<Station> stations;

    public Line() {
        stations = new ArrayList<Station>();
    }

    public Line(String numberLine, String nameLine, Color colorLine) {
        this();
        this.numberLine = numberLine;
        this.nameLine = nameLine;
        this.colorLine = colorLine;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    public String getNameLine() {
        return nameLine;
    }

    public void setNameLine(String nameLine) {
        this.nameLine = nameLine;
    }

    public Color getColorLine() {
        return colorLine;
    }

    public void setColorLine(Color colorLine) {
        this.colorLine = colorLine;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public void addStation(Station station){
        stations.add(station);
    }

    @Override
    public int compareTo(Line line) {
        Double l1;
        Double l2;
        if(numberLine.equals("8А")){
            l1 = 8.5;
        }else if(numberLine.equals("011А")){
            l1 = 11.5;
        }else l1 = Double.parseDouble(numberLine);

        if(line.getNumberLine().equals("8А")){
            l2 = 8.5;
        }else if(line.getNumberLine().equals("011А")){
            l2 = 11.5;
        }else l2 = Double.parseDouble(line.getNumberLine());

        return l1.compareTo(l2);
//                numberLine.compareTo(line.getNumberLine());
    }

    @Override
    public String toString() {
        return "Line{" +
                "numberLine= '" + numberLine + '\'' +
                ", nameLine= '" + nameLine + '\'' +
                ", colorLine= " + colorLine +
                '}';
    }
}
