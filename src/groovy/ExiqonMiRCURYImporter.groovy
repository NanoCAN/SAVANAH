import org.nanocan.savanah.library.Library
import org.nanocan.security.Person
import org.nanocan.savanah.plates.Plate
import org.nanocan.layout.PlateLayout
import org.nanocan.layout.WellLayout
import org.nanocan.layout.Sample
import java.awt.Color
import org.nanocan.layout.Identifier
/**
 * Created with IntelliJ IDEA.
 * User: markus
 * Date: 7/29/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
class ExiqonMiRCURYImporter {

    def import(filePath){

        def user = Person.findByUsername("mlist")
        Random random = new Random();

        //create library

        new Library(createdBy: user, dateCreated: new Date(),
                lastUpdated: new Date(), lastUpdatedBy: user,
                type: "microRNA inhibitor").save(flush: true)


        for(int i=0; i<=12; i++){

            //define plate layouts for all 12 plates:
            def plateLayout = new PlateLayout(format: "96-well", controlPlate: false, name: "Exiqon miRCURY Inhibitor Library Plate ${i} Layout")

            for(int row = 1; row <= 8; row++)
            {
                for(int col = 1; col <= 12; col++)
                {
                    //generate random color
                    final float hue = random.nextFloat();
                    // Saturation between 0.1 and 0.3
                    final float saturation = (random.nextInt(2000) + 1000) / 10000f;
                    final float luminance = 0.9f;
                    final Color color = Color.getHSBColor(hue, saturation, luminance);
                    String rgb = Integer.toHexString(color.getRGB());
                    rgb = rgb.substring(2, rgb.length());

                    def sample = new Sample(color: rgb,  name: currentLine[3], type: "miRNA inhibitor", targetGene: null)
                    sample.addToIdentifiers(new Identifier(accessionNumber: currentLine[4], type: "miRBase", name: currentLine[3]))
                    plateLayout.addToWells(new WellLayout(row: row, col: col, sample: sample))
                }
            }

            def barcode = "E"
            new Plate(barcode: "E${i}", format: "96-well", family: "library", libraryPlate: null,
            parentPlate: null, plateType: null, name: "Exiqon miRCURY Inhibitor Library Plate ${i}",
            layout: plateLayout)

        }



    }
}
