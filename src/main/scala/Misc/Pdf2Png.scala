package Misc

import org.apache.log4j.{Level, Logger}
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.{ImageType, PDFRenderer}
import org.apache.pdfbox.tools.imageio.ImageIOUtil
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import scala.collection.JavaConversions._
import java.io.File

object Pdf2Png {

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .master("local")
      .appName("Test")
      .getOrCreate()

    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.ERROR)

    val Path = "Data/PDFs"
    getListOfFiles(Path).foreach(x => {

      val file = new File(x);

      val filpath = file.getParentFile
      val filename = file.getName
      val index = file.getName.lastIndexOf('.')

      val filenamewithExt = file.getName().substring(0, index)

      val document = PDDocument.load(file)
      val pdfRenderer = new PDFRenderer(document)
      var pageCounter = 0
      for (page <- document.getPages) {
        // note that the page number parameter is zero based
        val bim = pdfRenderer.renderImageWithDPI(pageCounter, 300, ImageType.RGB)
        // suffix in filename will be used as the file format
        pageCounter = pageCounter + 1
        ImageIOUtil.writeImage(bim, "Data/PNGs/" + filenamewithExt + "_" + pageCounter + ".png", 300)
      }
    })
  }

  def getListOfFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile)
      .map(_.getPath).toList
  }

}
