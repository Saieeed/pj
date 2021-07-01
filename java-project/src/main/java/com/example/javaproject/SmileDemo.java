package com.example.javaproject;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import smile.data.DataFrame;
import smile.data.vector.BaseVector;
import smile.io.Read;
import smile.plot.swing.Canvas;
import smile.plot.swing.Histogram;

import javax.imageio.ImageIO;
import javax.ws.rs.Produces;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;



@Data
@AllArgsConstructor

@RestController
public class SmileDemo {

    String trainPath = "src/main/resources/data/Mobile_train.csv";
    String testPath = "src/main/resources/data/Mobile_test.csv";
    @RequestMapping("/haha")
    @ResponseBody
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sad");
        return modelAndView;
    }
    public SmileDemo() {
    }
    @RequestMapping(method = RequestMethod.POST,path = "/hi")
    @Produces("img/html")
    public @ResponseBody static String main(String[] args) {

        SmileDemo sd = new SmileDemo ();
        DataFrame trainData = sd.readCSV (sd.trainPath);
        DataFrame testData = sd.readCSV (sd.testPath);
        sd.getTrainDataSummery (trainData);
        sd.processTrainData (trainData);
        BufferedImage x =  sd.plotData (trainData) ;

        try {// retrieve image
            File outputfile = new File("src/main/resources/data/image.jpg");
            ImageIO.write(x, "png", outputfile);
        } catch (IOException e) {
            // handle exception
        }
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2>HTML Imag mnnkjne</h2>\n" +
                "<img src=\"@{images/image.jpg}\"/>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }


    public DataFrame readCSV(String path) {

        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame df = null;
        try {
            df = Read.csv (path, format);
        } catch (IOException e) {
            e.printStackTrace ();
        } catch (URISyntaxException e) {
            e.printStackTrace ();
        }
        System.out.println (df.summary ());
        return df;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getTrainDataSummery(DataFrame data) {
        DataFrame summary = data.summary ();
        DataFrame selectedColumns = data.select ("battery_power", "n_cores");

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public DataFrame processTrainData(DataFrame data){
       DataFrame nonNullData= data.omitNullRows ();
        System.out.println ("Number of non Null rows is: "+nonNullData.nrows ());
        BaseVector talk_timeDF=nonNullData.column ("talk_time");

        return nonNullData;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    BufferedImage plotData(DataFrame data) {

          DataFrame selectedDF = data.select ("clock_speed","int_memory");
            Canvas canvas = Histogram.of (selectedDF.doubleVector (0).array ()).canvas ();

            return   canvas.toBufferedImage(1800,720);


    }
}
