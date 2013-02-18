/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.isas.peptideshaker.export.section_generators;

import com.compomics.util.preferences.PTMScoringPreferences;
import eu.isas.peptideshaker.export.ExportFeature;
import eu.isas.peptideshaker.export.exportfeatures.PtmScoringFeatures;
import eu.isas.peptideshaker.export.exportfeatures.SpectrumCountingFeatures;
import eu.isas.peptideshaker.preferences.SpectrumCountingPreferences;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class outputs the spectrum counting related export features
 *
 * @author Marc
 */
public class SpectrumCountingSection {

    /**
     * The features to export
     */
    private ArrayList<ExportFeature> exportFeatures;
    /**
     * The separator used to separate columns
     */
    private String separator;
    /**
     * Boolean indicating whether the line shall be indexed
     */
    private boolean indexes;
    /**
     * Boolean indicating whether column headers shall be included
     */
    private boolean header;
    /**
     * The writer used to send the output to file.
     */
    private BufferedWriter writer;

    /**
     * constructor
     *
     * @param exportFeatures the features to export in this section
     * @param separator
     * @param indexes
     * @param header
     * @param writer
     */
    public SpectrumCountingSection(ArrayList<ExportFeature> exportFeatures, String separator, boolean indexes, boolean header, BufferedWriter writer) {
        this.exportFeatures = exportFeatures;
        this.separator = separator;
        this.indexes = indexes;
        this.header = header;
        this.writer = writer;
    }

    /**
     * Writes the desired section
     *
     * @param spectrumCountingPreferences the spectrum countinge preferences of
     * this project
     * @throws IOException exception thrown whenever an error occurred while
     * writing the file.
     */
    public void writeSection(SpectrumCountingPreferences spectrumCountingPreferences) throws IOException {
        if (header) {
            if (indexes) {
                writer.write(separator);
            }
            writer.write("Parameter" + separator + "Value");
            writer.newLine();
        }
        int line = 1;
        for (ExportFeature exportFeature : exportFeatures) {
            if (indexes) {
                writer.write(line + separator);
            }
            writer.write(exportFeature.getTitle() + separator);
            SpectrumCountingFeatures spectrumCountingFeatures = (SpectrumCountingFeatures) exportFeature;
            switch (spectrumCountingFeatures) {
                case method:
                    switch (spectrumCountingPreferences.getSelectedMethod()) {
                        case EMPAI:
                            writer.write("emPAI");
                            break;
                        case NSAF:
                            writer.write("NSAF");
                            break;
                        default:
                            writer.write("unknown");
                    }
                    break;
                case validated:
                    if (spectrumCountingPreferences.isValidatedHits()) {
                        writer.write("Yes");
                    } else {
                        writer.write("No");
                    }
                    break;
                default:
                    writer.write("Not implemented");
            }
            writer.newLine();
            line++;
        }
    }
}
