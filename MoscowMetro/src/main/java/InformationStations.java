import model.Line;
import model.Station;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class InformationStations {
    private HashMap<String, Line> lines;
    private TreeSet<Station> stations;
    private TreeMap<Station, TreeSet<Station>> connections;

    public InformationStations() {
        lines = new HashMap<>();
        stations = new TreeSet<>();
        connections = new TreeMap<>();
    }

    public HashMap<String, Line> getLines() {
        return lines;
    }

    public void setLines(HashMap<String, Line> lines) {
        this.lines = lines;
    }

    public TreeSet<Station> getStations() {
        return stations;
    }

    public void setStations(TreeSet<Station> stations) {
        this.stations = stations;
    }

    public TreeMap<Station, TreeSet<Station>> getConnections() {
        return connections;
    }

    public void setConnections(TreeMap<Station, TreeSet<Station>> connections) {
        this.connections = connections;
    }
}
