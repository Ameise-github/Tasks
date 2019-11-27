package parse;

import model.Color;
import model.Line;
import model.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseFileHtml {
    private Document docHTML;
    private TreeSet<Line> LinesSet;
    private TreeSet<Station> StationsSet;
    private TreeMap<Station, ArrayList<String>> ConnMapTmp;
    private TreeMap<Station, ArrayList<Station>> ConnectionsMap;

    public ParseFileHtml() {
        LinesSet = new TreeSet<>();
        StationsSet = new TreeSet<>();
        ConnMapTmp = new TreeMap<>();
        ConnectionsMap = new TreeMap<>();
    }

    /**
     * Парсинг html страницы по ссылки
     */
    public void parsHTML(Document doc) {
        Color[] colorsArray = Color.values();
        //нашли таблицу
        Elements tables = doc.select("table.standard.sortable tbody");
        //Нашли строки
        Elements trs = tables.select("tr");
        for (Element tr : trs) {
            //Линия
            Elements lines = tr.select("tr td[style]");
            if (lines.size() == 0) {
                lines = tr.select("tr.shadow td");
            }
            if (lines.size() != 0) {
                Line findLine = null;
                Line findLine2 = null;

                for (Element l : lines) {
                    String colorTmp = l.attr("style");
                    Color colorLine;
                    if (colorTmp.equals("")) {
                        colorLine = Color.UNKNOWN;
                    } else {
                        String[] colorArr = colorTmp.split(":");
                        colorLine = Color.UNKNOWN;
                        for (Color c : colorsArray) {
                            if (colorArr[1].equals("background-color")) {
                                colorLine = Color.YELLOW;
                                findLine = new Line(l.selectFirst("span.sortkey").text(), (l.selectFirst("span[title]").attr("title")).trim(), colorLine);
                                findLine2 = new Line("11", "Большая кольцевая линия", Color.TURQUOISE);
                            }
                            if (colorArr[1].equals(c.getCode())) colorLine = c;
                        }
                    }

                    if (findLine == null) {
                        findLine = new Line(l.selectFirst("span.sortkey").text(), (l.selectFirst("span[title]").attr("title")).trim(), colorLine);
                    }
                    if (findLine2 != null) {
                        LinesSet.add(findLine);
                        LinesSet.add(findLine2);
                    } else LinesSet.add(findLine);
                }

                //Станции
                String a = tr.select("td a").text();
                Pattern p;
                Matcher m;
                int st = 0;
                if (a.contains("Улица 1905 года")) {
                    p = Pattern.compile("3\\d");
                    m = p.matcher(a);
                    if (m.find()) {
                        st = m.start();
                    }
                } else {
                    p = Pattern.compile("\\d");
                    m = p.matcher(a);
                    if (m.find()) {
                        st = m.start();
                    }
                }
                Station findStation = new Station(findLine, a.substring(0, st).trim());
                if (findLine2 != null) {
                    Station findStation2 = new Station(findLine2, a.substring(0, st).trim());
                    StationsSet.add(findStation2);
                }
                if (findStation.getNameStation().equals("Деловой центр")) {
                    if (!findLine.getNumberLine().equals("8А")) {
                        StationsSet.add(new Station(findLine, a.substring(0, st).trim()));
                    }
                } else StationsSet.add(findStation);


                //Пересадки
                Elements connectionsTemp = tr.select("td[data-sort-value]");
                Elements connections;
                if(tr.className().equals("shadow")){
                    connections = connectionsTemp.eq(1).select("span");
                }else connections = connectionsTemp.select("td:not([style]) span");
                if (connections.size() != 0) {
                    ArrayList<String> connectionListTmp = new ArrayList<>();
                    String nls = connections.parents().attr("data-sort-value");
                    Elements nameConnections = connections.select("span[title]");
                    if (nameConnections.size() != 0) {
                        connectionListTmp.add(nls.trim());
                        for (Element namC : nameConnections) {
                            connectionListTmp.add(namC.attr("title").trim());
                        }
                    }
                    ConnMapTmp.put(findStation, connectionListTmp);
                }
            }
        }
        /*Заполнение List станций в каждой линии*/
        LinesSet.forEach(l -> {
            StationsSet.forEach(s -> {
                if (s.getLine().getNumberLine().equals(l.getNumberLine())) {
                    l.addStation(s);
                }
            });
        });
        /*Вызов функции формирования пересадок*/
        getConnectionsMap();
    }

    /**
     * Получение документа по URL
     */
    public Document getDocHTML(String URL) throws IOException {
        docHTML = Jsoup.connect(URL).maxBodySize(0).get();
        return docHTML;
    }

    public Document getDocHTML() {
        return docHTML;
    }

    public void setDocHTML(Document docHTML) {
        this.docHTML = docHTML;
    }

    public TreeSet<Line> getLinesSet() {
        return LinesSet;
    }

    public Line getLine(String numberLine) {
        for (Line l : LinesSet) {
            if (l.getNumberLine().equals(numberLine)) {
                return l;
            }
        }
        return null;
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

    private TreeMap<Station, ArrayList<String>> getConnMapTmp() {
        return ConnMapTmp;
    }

    private void setConnMapTmp(TreeMap<Station, ArrayList<String>> connMapTmp) {
        ConnMapTmp = connMapTmp;
    }

    public TreeMap<Station, ArrayList<Station>> getConnectionsMap() {
        ArrayList<String> nameStationCon = new ArrayList<>();
        ConnMapTmp.forEach((k, v) -> {
//            System.out.println("Station: " + k.getLine().getNumberLine() + "   " + k.getNameStation());
//            System.out.println("\t" + v);
            for (int i = 0; i < v.size(); i++) {
                if (i == 0) {
//                    System.out.println("number Line: " + v.get(i));
                    nameStationCon.clear();
                } else {
                    int start = v.get(i).indexOf('ю') + 2;
                    int end = v.get(i).indexOf(' ', start);
                    String tmp = v.get(i).substring(start, end);
//                    System.out.println("\t" + tmp);
                    nameStationCon.add(tmp);
                }
            }
//            System.out.println("=====ОБРАБОТАННОЕ!!!!=====");
            ArrayList<Station> connectStation = getNameStationConnection(v.get(0), nameStationCon);
            if (connectStation.size() != 0) {
//                connectStation.forEach(ss -> System.out.println("Line num: " + ss.getLine().getNumberLine() + "  Stat: " + ss.getNameStation()));
                ConnectionsMap.put(k, connectStation);
            }
        });
        return ConnectionsMap;
    }

    /*запись в файл json*/
    public JSONObject getJsonFile(TreeSet<Line> Lines) {
        JSONObject outputJson = new JSONObject();
        //Формирование станций в Json файле
        JSONObject stationsJson = new JSONObject();
        Lines.forEach(l -> {
            JSONArray StationJsonArr = new JSONArray();
            StationJsonArr.addAll(l.getStations());
            stationsJson.put(l.getNumberLine(), StationJsonArr);
        });
        //Добавление станций в файл json
        outputJson.put("stations", stationsJson);
        //Формирование линий в Json файле
        JSONArray LinesArrayJson = new JSONArray();
        Lines.forEach(l -> {
            JSONObject lineObj = new JSONObject();
            lineObj.put("number", l.getNumberLine());
            lineObj.put("name", l.getNameLine());
            lineObj.put("color", "" + l.getColorLine() + "");
            LinesArrayJson.add(lineObj);
        });
        //Добавление линий в файл json
        outputJson.put("lines", LinesArrayJson);
        //Формирование пересадок в Json файле
        JSONArray ConnectionsArrayJson = new JSONArray();
        ConnectionsMap.forEach((k, v) -> {
            JSONArray tmp = new JSONArray();
            JSONObject StObj = new JSONObject();
            StObj.put("line", k.getLine().getNumberLine());
            StObj.put("station", k.getNameStation());
            tmp.add(StObj);
            v.forEach(station -> {
                JSONObject StConObj = new JSONObject();
                StConObj.put("line", station.getLine().getNumberLine());
                StConObj.put("station", station.getNameStation());
                tmp.add(StConObj);
            });
            ConnectionsArrayJson.add(tmp);
        });
        outputJson.put("connections", ConnectionsArrayJson);

        return outputJson;
    }

    //поиск станий пересадок
    private ArrayList<Station> getNameStationConnection(String numberLineConnection, ArrayList<String> nameStationConnection) {
        ArrayList<Station> connectStation = new ArrayList<>();
        ArrayList<Line> line = new ArrayList<>();

        //поиск линии
        if (numberLineConnection.equals("8.5")) {
            line.add(getLine("8А"));
        } else if (numberLineConnection.equals("11.5")) {
            line.add(getLine("011А"));
        } else if (numberLineConnection.contains("8.5")) {
            int start = numberLineConnection.indexOf("8.5");
            String numbLine2 = numberLineConnection.substring(start + 3);
            line.add(getLine("8А"));
            line.add(getLine(numbLine2));
        } else if (numberLineConnection.contains("85")) {
            String numbLine2 = "0" + numberLineConnection.charAt(0);
            line.add(getLine("8А"));
            line.add(getLine(numbLine2));
        } else {
            String[] arrayNumber = numberLineConnection.split("\\.");
            if (arrayNumber.length == 1) {
                if (arrayNumber[0].length() == 2) {
                    line.add(getLine(numberLineConnection));
                } else {
                    line.add(getLine("0" + arrayNumber[0]));
                }
            } else {
                if (arrayNumber[0].length() == 2) {
                    line.add(getLine(arrayNumber[0]));
                } else {
                    line.add(getLine("0" + arrayNumber[0]));
                }
                if (arrayNumber[1].length() == 2) {
                    line.add(getLine(arrayNumber[1]));
                } else {
                    int start = 0;
                    int end = 0;
                    for (int i = 2; i < arrayNumber[1].length() + 1; i += 2) {
                        end = i;
                        line.add(getLine(arrayNumber[1].substring(start, end)));
                        start = end;
                    }
                }
            }
        }
        //Формирование станций пересадок
        if (line.size() != 0) {
            for (int i = 0; i < nameStationConnection.size(); i++) {
                List<Station> st = line.get(i).getStations();
                for (int j = 0; j < st.size(); j++) {
                    if (st.get(j).getNameStation().contains(nameStationConnection.get(i))) {
                        connectStation.add(st.get(j));
                        break;
                    }
                }
            }
        }

        return connectStation;
    }

}
