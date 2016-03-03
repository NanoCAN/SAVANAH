package org.nanocan.savanah

class AccessionsTagLib {

    /**
     * Creates a link that redirects to a public database for a given accession
     *
     * @attr type REQUIRED the accession type
     * @attr accession REQUIRED the unique identifier
     */
    def accessionFormat = { attrs, body ->
        if (attrs.type == "miRBase mature")
            out << "<a target='_blank' href='http://www.mirbase.org/cgi-bin/mature.pl?mature_acc=${attrs.accession}'>${attrs.accession}</a>"
        else if(attrs.type == "miRBase miRNA")
            out << "<a target='_blank' href='http://www.mirbase.org/cgi-bin/mirna_entry.pl?acc=${attrs.accession}'>${attrs.accession}</a>"
        else if(attrs.type == "Entrez")
            out << "<a target='_blank' href='http://www.ncbi.nlm.nih.gov/gene/${attrs.accession}'>${attrs.accession}</a>"
        else
            out << "<a target='_blank' href='http://www.google.com/search?q=${attrs.accession}'>${attrs.accession}</a>"
    }
}
