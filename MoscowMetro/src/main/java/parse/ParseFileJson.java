package parse;

import model.Color;
import model.Line;
import model.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class ParseFileJson {
    private TreeSet<Line> LinesSet;
    private TreeSet<Station> StationsSet;
    private TreeMap<Station, ArrayList<String>> ConnMapTmp;

    public ParseFileJson() {
        LinesSet = new TreeSet<>();
        StationsSet = new TreeSet<>();
        ConnMapTmp = new TreeMap<>();
    }

    public TreeSet<Line> getLinesSet() {
        return LinesSet;
    }

    public void setLinesSet(TreeSet<Line> linesSet) {
        LinesSet = linesSet;
    }

    public TreeSet<Station> getStationsSet() {
        return StationsSet;
    }

    public void setStationsSet(TreeSet<Station> stationsSet) {
        StationsSet = stationsSet;
    }

    public TreeMap<Station, ArrayList<String>> getConnMapTmp() {
        return ConnMapTmp;
    }

    public void setConnMapTmp(TreeMap<Station, ArrayList<String>> connMapTmp) {
        ConnMapTmp = connMapTmp;
    }

    /**
     * Получение информации по линиям метро
     */
    public void parselineJson(JSONArray JsonLineArr) {
        JsonLineArr.forEach(jsonObj -> {
            JSONObject lineJson = (JSONObject) jsonObj;
            Line line = new Line(
                    lineJson.get("number").toString(),
                    lineJson.get("name").toString(),
                    Color.valueOf(lineJson.get("color").toString())
            );
            LinesSet.add(line);
        });
    }

    /**
     * Получение информации по станциям метро
     */
    public void parseStationJson(JSONObject JsonStationObj) {
        JsonStationObj.keySet().forEach(numLinObj -> {
            Line line = getLine((String) numLinObj);
            if(line != null){
                JSONArray JsonStatArray = (JSONArray) JsonStationObj.get(numLinObj);
                JsonStatArray.forEach(statObj -> {
                    Station st = new Station(line,(String)statObj);
                    StationsSet.add(st);
                    line.addStation(st);
                });
            }else throw new IllegalArgumentException("Линия не найдена!");
        });
    }

    public Line getLine(String numberLine) {
        for (Line l : LinesSet) {
            if (l.getNumberLine().equals(numberLine)) {
                return l;
            }
        }
        return null;
    }
}
