import org.nanocan.savanah.library.Library
import org.nanocan.security.Person

/**
 * Created with IntelliJ IDEA.
 * User: markus
 * Date: 7/29/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
class ExiqonMiRCURYImporter {

    def libraryImport(filePath)
    {

        def user = Person.findByUsername("mlist")
        Random random = new Random();

        //create library

        new Library(createdBy: user, dateCreated: new Date(),
                lastUpdated: new Date(), lastUpdatedBy: user,
                type: "microRNA inhibitor").save(flush: true)



                 /*   //generate random color
                    final float hue = random.nextFloat();
                    // Saturation between 0.1 and 0.3
                    final float saturation = (random.nextInt(2000) + 1000) / 10000f;
                    final float luminance = 0.9f;
                    final Color color = Color.getHSBColor(hue, saturation, luminance);
                    String rgb = Integer.toHexString(color.getRGB());
                    rgb = rgb.substring(2, rgb.length());

                    def sample = new Sample(color: rgb,  name: currentLine[3], type: "miRNA inhibitor", targetGene: null)
                    sample.addToIdentifiers(new Identifier(accessionNumber: currentLine[4], type: "miRBase", name: currentLine[3]))
                    */

    }
}
