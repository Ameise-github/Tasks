import model.Color;
import model.Line;
import parse.ParseFileHtml;
import model.Station;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import parse.ParseFileJson;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.*;

public class Main {
    static public void main(String[] Args) {
        Color[] colorsArray = Color.values();
        TreeSet<Line> LinesSetHtml = new TreeSet<>();
        TreeSet<Station> StationsSetHtml = new TreeSet<>();
        TreeMap<Station, ArrayList<Station>> ConnectionsMap = new TreeMap<>();

        ParseFileHtml parseFileHtml = new ParseFileHtml();
        String url = "https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена";
        Document doc = null;
        try {
            doc = parseFileHtml.getDocHTML(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //парсер HTML файла
        parseFileHtml.parsHTML(doc);
        //получение линий
        LinesSetHtml = parseFileHtml.getLinesSet();
        //Получение станций
        StationsSetHtml = parseFileHtml.getStationsSet();
        //Формирование Json файла
        JSONObject outputJson = parseFileHtml.getJsonFile(LinesSetHtml);

        //Запись  файл MoscowMetro.json
        try (FileWriter file = new FileWriter("data/MoscowMetro.json")) {
            file.write(outputJson.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Получение Json объекта*/
        JSONObject JsonObjMetro = getJsonObjFromFile("data/MoscowMetro.json");
        ParseFileJson pfj = new ParseFileJson();
        //получение списка линий
        JSONArray JsonLineArr = (JSONArray) JsonObjMetro.get("lines");
        pfj.parselineJson(JsonLineArr);
        //получение списка станций
        JSONObject JsonStationObj = (JSONObject) JsonObjMetro.get("stations");
        pfj.parseStationJson(JsonStationObj);

        TreeSet<Line> LinesSetJson = new TreeSet<>();
        TreeSet<Station> StationsSetJson = new TreeSet<>();
        LinesSetJson = pfj.getLinesSet();
        StationsSetJson = pfj.getStationsSet();
        System.out.printf("%5s | %-35s | %5s \n","№ линии","Наименование","Количество станций");
        System.out.printf("%5s|%-20s|%5s \n","-".repeat(8), "-".repeat(37),"-".repeat(20));
        for (Line l : LinesSetJson){
            System.out.printf("%7s | %-35s | %5d \n",l.getNumberLine(),l.getNameLine(),l.getStations().size());
        }

        //Вывод пересадок
//        ConnectionsMap = parseFileHtml.getConnectionsMap();
//        ConnectionsMap.forEach((k,v) -> {
//            System.out.println("Station: " + k.getLine().getNumberLine() + "   " + k.getNameStation());
//            System.out.println("\tConnection: " + v + "\n");
//        });
    }

    /**
     * Получение Json объекта
     */
    private static JSONObject getJsonObjFromFile(String pathToFile) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObjectMetro = null;
        try {
            jsonObjectMetro = (JSONObject) parser.parse(new FileReader(pathToFile));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonObjectMetro;
    }
}


//    Document doc = null;
//        try {
//                doc = Jsoup.connect("https://ru.wikipedia.org/wiki/Список_станций_Московского_метрополитена").maxBodySize(0).get();
//                } catch (IOException e) {
//                e.printStackTrace();
//                }
//
//                //нашли таблицу
//                Elements tables = doc.select("table.standard.sortable tbody");
//                //Нашли строки
//                Elements trs = tables.select("tr");
//                for (Element tr : trs) {
//                //линия
//                Elements lines = tr.select("tr td[style]");
//                if (lines.size() != 0) {
//                Line findLine = null;
//                for (Element l : lines) {
////                    System.out.print("№Line =  " + l.selectFirst("span.sortkey").text() + "; ");
////                    System.out.print("  colorLine = " + l.attr("style") + "; ");
////                    System.out.print("  nameLine = " + l.selectFirst("span[title]").attr("title") + "\n");
//
//                String colorTmp = l.attr("style");
//                String[] colorArr = colorTmp.split(":");
//                Color colorLine = Color.UNKNOWN;
//                for(Color c : colorsArray){
//                if(colorArr[1].equals("background-color")){
//                colorLine = Color.YELLOW_BLUE;
//                }
//                if(colorArr[1].equals(c.getCode())) colorLine = c;
//                }
//                findLine = new Line(l.selectFirst("span.sortkey").text(),l.selectFirst("span[title]").attr("title"),colorLine);
//                LinesSet.add(findLine);
//                }
//                //Станции
////            Elements stations = tr.select("tr td span[style='white-space: nowrap;'] a"); old
////                tr.select("td a[title*=станция]").text() OLD
////                System.out.println("\t nameStation = " + tr.select("td a").text());
//                String a = tr.select("td a").text();
//                Pattern p;
//                Matcher m;
//                int st = 0;
//                if(a.contains("Улица 1905 года")){
//                p = Pattern.compile("3\\d");
//                m = p.matcher(a);
//                if (m.find()) {
//                st = m.start();
//                }
////                    else System.out.println("nothing 30");
//                }else{
//                p = Pattern.compile("\\d");
//                m = p.matcher(a);
//                if (m.find()) {
//                st = m.start();
//                }
////                    else System.out.println("nothing");
//                }
////                String findStation = a.substring(0,st);
////                System.out.println("nameStation = " + findStation);
//
//                Station findStation = new Station(findLine,a.substring(0,st));
//                StationsSet.add(findStation);
//
//                //Пересадки
//                Elements connectionsTemp = tr.select("td[data-sort-value]");
//                Elements connections = connectionsTemp.select("td:not([style]) span");
//                if (connections.size() != 0) {
//                ArrayList<String> connectionListTmp = new ArrayList<>();
////                    System.out.print(" numberStationConnection =  " + connections.parents().attr("data-sort-value") + ": \n");
//        String nls = connections.parents().attr("data-sort-value");
//        Elements nameConnections = connections.select("span[title]");
//        if (nameConnections.size() != 0) {
//        connectionListTmp.add(nls);
//        for (Element namC : nameConnections) {
////                            System.out.println(" \tnameStationConnection = " + namC.attr("title"));
//        connectionListTmp.add(namC.attr("title"));
//        }
//        }
//        ConnMapTmp.put(findStation,connectionListTmp);
//        }
////                else System.out.print(" numberStationConnection =  Infinity\n");
////                System.out.println();
//        }
//        }
//
//        /*Проверка*/
////        System.out.println("Line:");
////        LinesSet.forEach(System.out :: println);
////        System.out.println("\nStation = " + StationsSet.size());
////        StationsSet.forEach(System.out :: println);
////        System.out.println("\nConnections");
////        ConnMapTmp.forEach((k,v) -> System.out.println("Station:  " + k + " :" + " \t Connection: " + v));
//
//        /*Заполнение List станций в каждой линии*/
//        LinesSet.forEach(l -> {
//        StationsSet.forEach(s -> {
//        if(s.getLine().getNumberLine().equals(l.getNumberLine())){
//        l.addStation(s);
//        }
//        });
//        });