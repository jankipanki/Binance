import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteText {

    public static void main(String[] args) throws IOException {
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        String fileName = "PriceBinance_" + sDF.format(date) + ".csv";

        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        Connection connect = Jsoup.connect("https://api.binance.com/api/v3/ticker/price");
        Document document = connect.ignoreContentType(true).get();
        Elements allTd = document.select("html");
        String str = allTd.text();

        String finalStr = str
                .replace(":", "")
                .replace("symbol", "")
                .replace("price", "")
                .replace("[{", "")
                .replace("}]", "")
                .replace("\"", "")
                .replace(",", ";");

        String[] division = finalStr.split("};\\{");

        for (String word : division) {
            pw.print(word + "\n");
        } pw.close();

        System.out.println("Aktualne kursy walut zostały zaimportowane do pliku: " + fileName);
    }
}
