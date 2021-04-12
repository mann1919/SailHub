package com.example.sailhub;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class DisplaySeriesResult extends AppCompatActivity {

    String[] columnHeaders={"Rank","Class","SailNo","Helm","Crew","PY","Points"};
    ArrayList<FinalCompetitorData> competitorsList;
    String[][] records;
    String sName;
    TextView nameOfSeries;
    Button Print;
    DBHelper DB;
    WebView webView;
    public ArrayList<Integer> raceIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_series_result);
        webView=findViewById(R.id.webView);
        Print = (Button) findViewById(R.id.btnPrint);


        nameOfSeries = findViewById(R.id.tvSeriesNameFinal);
        sName = getIntent().getExtras().getString("seriesName");
        nameOfSeries.setText(sName);


        DB = DBHelper.getInstance(this);
        raceIds = new ArrayList<>();


        final TableView<String[]> tb = (TableView<String[]>) findViewById(R.id.finalTable);
        tb.setColumnCount(columnHeaders.length);
        tb.setHeaderBackgroundColor(Color.parseColor("#E4D5B3"));
        //POPULATE
        populateData(sName);

        //ADAPTERS
        SimpleTableHeaderAdapter headerAdapter = new SimpleTableHeaderAdapter(this, columnHeaders);
        headerAdapter.setPaddings(5,0,2,10);
        tb.setHeaderAdapter(headerAdapter);

        SimpleTableDataAdapter dataAdapter = new SimpleTableDataAdapter(this, records);
        dataAdapter.setPaddings(0,0,5,10);
        tb.setDataAdapter(dataAdapter);

   }

    private void populateData(String seriesName) {
        competitorsList = new ArrayList<>();
        {
            Cursor cursor = DB.readSeriesResult(seriesName);
            int rankCount = 1;
            while (cursor.moveToNext()) {

                int rank = rankCount;
                String bClass = cursor.getString(0);
                int sailNo = Integer.parseInt(cursor.getString(1));
                String helmName = cursor.getString(2);
                String crewName = cursor.getString(3) == null ? "" :cursor.getString(3) ;
                int PY = Integer.parseInt(cursor.getString(4));
                int points = cursor.getString(5) == null ? -1 : Integer.parseInt(cursor.getString(5));
                rankCount++;
                FinalCompetitorData competitor = new FinalCompetitorData(rank, bClass, sailNo, helmName, crewName, PY, points);
                competitorsList.add(competitor);
            }
            records = new String[competitorsList.size()][columnHeaders.length];
            for (int i = 0; i < competitorsList.size(); i++) {
                FinalCompetitorData s = competitorsList.get(i);

                records[i][0] = String.valueOf(s.getRank());
                records[i][1] = s.getBoatClass();
                records[i][2] = String.valueOf(s.getSailNo());
                records[i][3] = s.getHelmName();
                records[i][4] = s.getCrewName() == "" ? "--" : s.getCrewName();
                records[i][5] = String.valueOf(s.getPY());
                records[i][6] = s.getPoints() == -1 ? "--" : String.valueOf(s.getPoints());
            }//for
        }
    }//populate



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void CreatePdf(View view) throws IOException {
        String dir = Environment.getExternalStorageDirectory() + "/Documents/";
        String filePath = new File(dir, "result_" + System.currentTimeMillis() + ".pdf").toString();
        new File(filePath).createNewFile();

        FileOutputStream fOut = new FileOutputStream(filePath);
        PdfWriter pdfWriter = new PdfWriter(fOut);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph(sName)
                .setBold()
                .setUnderline()
                .setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(" "));

        Table table = new Table(new float[] { 80, 80, 80, 80, 80, 80, 80 });

        for (String header : columnHeaders)
            table.addCell(new Paragraph(header).setBold());

        for (FinalCompetitorData competitorData : competitorsList) {
            table.addCell(new Paragraph(competitorData.rank + ""));
            table.addCell(new Paragraph(competitorData.boatClass + ""));
            table.addCell(new Paragraph(competitorData.sailNo + ""));
            table.addCell(new Paragraph(competitorData.helmName + ""));
            table.addCell(new Paragraph(competitorData.crewName + ""));
            table.addCell(new Paragraph(competitorData.PY + ""));
            table.addCell(new Paragraph(competitorData.points + ""));
        }

        document.add(table);

        document.close();
    }


}
