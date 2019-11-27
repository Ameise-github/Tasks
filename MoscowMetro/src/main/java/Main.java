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
