package Misc

import java.io.{BufferedWriter, File, FileWriter, IOException}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SparkSession}
import org.bytedeco.javacpp.lept.{pixDestroy, pixRead}
import org.bytedeco.javacpp.tesseract

case class Chart(filename: String, Hocr: String, Ocr: String)

object Png2Hocr {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .master("local")
      .appName("Test")
      .getOrCreate()

    val savePath = "Output"
    val path = "data/PNGs"

    getListOfFiles(path).foreach(x => {

      val chart = procOCR(x)

      writeFile(savePath, chart.filename + ".html", chart.Hocr)
      writeFile(savePath, chart.filename + ".txt", chart.Ocr)
    })

  }

  def getListOfFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile)
      .map(_.getPath).toList
  }

  def writeFile(directoryName: String, fileName: String, value: String): Unit = {

    val directory = new File(directoryName)
    if (!directory.exists) {
      directory.mkdir
      // If you require it to make the entire directory path including parents,
      // use directory.mkdirs(); here instead.
    }
    val file = new File(directoryName + "/" + fileName)
    try {
      val fw = new FileWriter(file.getAbsoluteFile)
      val bw = new BufferedWriter(fw)
      bw.write(value)
      bw.close()
    } catch {
      case e: IOException =>
        e.printStackTrace()
        System.exit(-1)
    }
  }

  def procOCR(path: String): Chart = {
    val TESSDATA_PREFIX = "data/tesseract-ocr-3.02/"
    val lang = "eng"
    val t = tesseract.TessBaseAPICreate
    val rc = tesseract.TessBaseAPIInit3(t, TESSDATA_PREFIX, lang)

    if (rc != 0) {
      tesseract.TessBaseAPIDelete(t)
      println("Init failed")
      sys.exit(3)
    }
    var path1 = path
    if (path.charAt(7) == ':')
      path1 = path.substring(6, path.length)


    val image = pixRead(path1)
    t.SetImage(image)
    val f = new File(path1)
    val fileName = f.getName
    val filenamewithoutExt = fileName.substring(0, fileName.lastIndexOf('.'))

    val ocr = t.GetUTF8Text.getString
    val hocr = t.GetHOCRText(0).getString //.replaceFirst("page_1", "page_" + PageNumString )
    //val ocrText = (scala.xml.XML.loadString(hocr).text).
    t.End
    pixDestroy(image)
    Chart(filenamewithoutExt, hocr, ocr)
  }
}
