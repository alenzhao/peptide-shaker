package eu.isas.peptideshaker.gui.tabpanels;

import com.compomics.util.examples.BareBonesBrowserLaunch;
import com.compomics.util.experiment.identification.matches.ProteinMatch;
import com.compomics.util.experiment.identification.matches.SpectrumMatch;
import eu.isas.peptideshaker.gui.PeptideShakerGUI;
import eu.isas.peptideshaker.gui.tablemodels.PeptideFractionTableModel;
import eu.isas.peptideshaker.gui.tablemodels.ProteinFractionTableModel;
import eu.isas.peptideshaker.gui.tablemodels.ProteinTableModel;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import no.uib.jsparklines.extra.HtmlLinksRenderer;
import no.uib.jsparklines.extra.TrueFalseIconRenderer;
import no.uib.jsparklines.renderers.JSparklinesBarChartTableCellRenderer;
import org.jfree.chart.plot.PlotOrientation;

/**
 * Displays information about which fractions the peptides and proteins were
 * detected in.
 *
 * @author Marc Vaudel
 * @author Harald Barsnes
 */
public class ProteinFractionsPanel extends javax.swing.JPanel {

    /**
     * A reference to the main PeptideShakerGUI.
     */
    private PeptideShakerGUI peptideShakerGUI;
    /**
     * The list of protein keys.
     */
    private ArrayList<String> proteinKeys;
    /**
     * A list of the peptides in the peptide table.
     */
    private ArrayList<String> peptideKeys = new ArrayList<String>();

    /**
     * Creates a new ProteinFractionsPanel.
     *
     * @param peptideShakerGUI
     */
    public ProteinFractionsPanel(PeptideShakerGUI peptideShakerGUI) {
        initComponents();
        this.peptideShakerGUI = peptideShakerGUI;
        setupGui();
        setTableProperties();
    }

    /**
     * Set up the GUI.
     */
    private void setupGui() {

        // set main table properties
        proteinTable.getTableHeader().setReorderingAllowed(false);
        peptideTable.getTableHeader().setReorderingAllowed(false);

        proteinTable.setAutoCreateRowSorter(true);
        peptideTable.setAutoCreateRowSorter(true);

        // make sure that the scroll panes are see-through
        proteinTableScrollPane.getViewport().setOpaque(false);
        peptideTableScrollPane.getViewport().setOpaque(false);

        // set up the table header tooltips
        setUpTableHeaderToolTips();
    }

    /**
     * Set up the table header tooltips.
     */
    private void setUpTableHeaderToolTips() {
        // @TODO: implement me!
    }

    /**
     * Set up the properties of the tables.
     */
    private void setTableProperties() {
        setProteinTableProperties();
        setPeptideTableProperties();
    }

    /**
     * Set up the properties of the protein table.
     */
    private void setProteinTableProperties() {

        // the index column
        proteinTable.getColumn(" ").setMaxWidth(50);
        proteinTable.getColumn(" ").setMinWidth(50);

        proteinTable.getColumn("Confidence").setMaxWidth(90);
        proteinTable.getColumn("Confidence").setMinWidth(90);

        // the validated column
        proteinTable.getColumn("").setMaxWidth(30);
        proteinTable.getColumn("").setMinWidth(30);

        proteinTable.getColumn("Accession").setCellRenderer(new HtmlLinksRenderer(peptideShakerGUI.getSelectedRowHtmlTagFontColor(), peptideShakerGUI.getNotSelectedRowHtmlTagFontColor()));

        proteinTable.getColumn("Confidence").setCellRenderer(new JSparklinesBarChartTableCellRenderer(PlotOrientation.HORIZONTAL, 100.0, peptideShakerGUI.getSparklineColor()));
        ((JSparklinesBarChartTableCellRenderer) proteinTable.getColumn("Confidence").getCellRenderer()).showNumberAndChart(
                true, peptideShakerGUI.getLabelWidth() - 20, peptideShakerGUI.getScoreAndConfidenceDecimalFormat());

        proteinTable.getColumn("").setCellRenderer(new TrueFalseIconRenderer(
                new ImageIcon(this.getClass().getResource("/icons/accept.png")),
                new ImageIcon(this.getClass().getResource("/icons/Error_3.png")),
                "Validated", "Not Validated"));

        for (int i = 2; i < proteinTable.getColumnCount() - 2; i++) {
            if (peptideShakerGUI.showSparklines()) {
                proteinTable.getColumn(proteinTable.getColumnName(i)).setCellRenderer(new JSparklinesBarChartTableCellRenderer(PlotOrientation.HORIZONTAL, 100.0, peptideShakerGUI.getSparklineColor()));
                ((JSparklinesBarChartTableCellRenderer) proteinTable.getColumn(proteinTable.getColumnName(i)).getCellRenderer()).showNumberAndChart(
                        true, peptideShakerGUI.getLabelWidth() - 20, peptideShakerGUI.getScoreAndConfidenceDecimalFormat());
            } else {
                ((JSparklinesBarChartTableCellRenderer) proteinTable.getColumn(proteinTable.getColumnName(i)).getCellRenderer()).showNumbers(true);
            }
        }
    }

    /**
     * Set up the properties of the peptide table.
     */
    private void setPeptideTableProperties() {

        // the index column
        peptideTable.getColumn(" ").setMaxWidth(50);
        peptideTable.getColumn(" ").setMinWidth(50);

        peptideTable.getColumn("Confidence").setMaxWidth(90);
        peptideTable.getColumn("Confidence").setMinWidth(90);

        // the validated column
        peptideTable.getColumn("").setMaxWidth(30);
        peptideTable.getColumn("").setMinWidth(30);

        peptideTable.getColumn("Confidence").setCellRenderer(new JSparklinesBarChartTableCellRenderer(PlotOrientation.HORIZONTAL, 100.0, peptideShakerGUI.getSparklineColor()));
        ((JSparklinesBarChartTableCellRenderer) peptideTable.getColumn("Confidence").getCellRenderer()).showNumberAndChart(
                true, peptideShakerGUI.getLabelWidth() - 20, peptideShakerGUI.getScoreAndConfidenceDecimalFormat());

        peptideTable.getColumn("").setCellRenderer(new TrueFalseIconRenderer(
                new ImageIcon(this.getClass().getResource("/icons/accept.png")),
                new ImageIcon(this.getClass().getResource("/icons/Error_3.png")),
                "Validated", "Not Validated"));

        for (int i = 2; i < peptideTable.getColumnCount() - 2; i++) {
            if (peptideShakerGUI.showSparklines()) {
                peptideTable.getColumn(peptideTable.getColumnName(i)).setCellRenderer(new JSparklinesBarChartTableCellRenderer(PlotOrientation.HORIZONTAL, 100.0, peptideShakerGUI.getSparklineColor()));
                ((JSparklinesBarChartTableCellRenderer) peptideTable.getColumn(peptideTable.getColumnName(i)).getCellRenderer()).showNumberAndChart(
                        true, peptideShakerGUI.getLabelWidth() - 20, peptideShakerGUI.getScoreAndConfidenceDecimalFormat());
            } else {
                ((JSparklinesBarChartTableCellRenderer) peptideTable.getColumn(peptideTable.getColumnName(i)).getCellRenderer()).showNumbers(true);
            }
        }
    }

    /**
     * Display the results.
     */
    public void displayResults() {

        proteinKeys = peptideShakerGUI.getIdentificationFeaturesGenerator().getProcessedProteinKeys(null);

        // update the table model
        if (proteinTable.getModel() instanceof ProteinFractionTableModel
                && ((ProteinFractionTableModel) proteinTable.getModel()).isModelInitiated()) {
            ((ProteinFractionTableModel) proteinTable.getModel()).updateDataModel(peptideShakerGUI);
        } else {
            ProteinFractionTableModel proteinTableModel = new ProteinFractionTableModel(peptideShakerGUI);
            proteinTable.setModel(proteinTableModel);
        }

        ((DefaultTableModel) proteinTable.getModel()).fireTableDataChanged();
        setProteinTableProperties();
        showSparkLines(peptideShakerGUI.showSparklines());

        updateSelection();
        proteinTable.requestFocus();

        peptideShakerGUI.setUpdated(PeptideShakerGUI.PROTEIN_FRACTIONS_TAB_INDEX, true);
    }

    /**
     * Update the selection.
     */
    public void updatePeptideTable() {
        int row = proteinTable.getSelectedRow();
        int proteinIndex = proteinTable.convertRowIndexToModel(row);
        String proteinKey = proteinKeys.get(proteinIndex);
        peptideKeys = peptideShakerGUI.getIdentificationFeaturesGenerator().getSortedPeptideKeys(proteinKey);

        // update the table model
        if (peptideTable.getModel() instanceof PeptideFractionTableModel
                && ((PeptideFractionTableModel) peptideTable.getModel()).isModelInitiated()) {
            ((PeptideFractionTableModel) peptideTable.getModel()).updateDataModel(peptideShakerGUI, peptideKeys);
        } else {
            PeptideFractionTableModel peptideTableModel = new PeptideFractionTableModel(peptideShakerGUI, peptideKeys);
            peptideTable.setModel(peptideTableModel);
        }

        ((DefaultTableModel) peptideTable.getModel()).fireTableDataChanged();
        setPeptideTableProperties();
        showSparkLines(peptideShakerGUI.showSparklines());

        // update the border titles
        ((TitledBorder) proteinPanel.getBorder()).setTitle("Proteins (" + proteinTable.getRowCount() + ")");
        proteinPanel.repaint();

        ((TitledBorder) peptidePanel.getBorder()).setTitle("Peptides (" + peptideTable.getRowCount() + ")");
        peptidePanel.repaint();

        // select the first row in the table
        if (peptideTable.getRowCount() > 0) {
            peptideTable.setRowSelectionInterval(0, 0);
        }
    }

    /**
     * Update the selected protein and peptide.
     */
    public void updateSelection() {

        int proteinRow = 0;
        String proteinKey = peptideShakerGUI.getSelectedProteinKey();
        String peptideKey = peptideShakerGUI.getSelectedPeptideKey();
        String psmKey = peptideShakerGUI.getSelectedPsmKey();

        if (proteinKey.equals(PeptideShakerGUI.NO_SELECTION)
                && peptideKey.equals(PeptideShakerGUI.NO_SELECTION)
                && !psmKey.equals(PeptideShakerGUI.NO_SELECTION)) {
            if (peptideShakerGUI.getIdentification().matchExists(psmKey)) {
                SpectrumMatch spectrumMatch = peptideShakerGUI.getIdentification().getSpectrumMatch(psmKey);
                peptideKey = spectrumMatch.getBestAssumption().getPeptide().getKey();
            } else {
                peptideShakerGUI.resetSelectedItems();
            }
        }

        if (proteinKey.equals(PeptideShakerGUI.NO_SELECTION)
                && !peptideKey.equals(PeptideShakerGUI.NO_SELECTION)) {
            for (String possibleKey : peptideShakerGUI.getIdentification().getProteinIdentification()) {
                ProteinMatch proteinMatch = peptideShakerGUI.getIdentification().getProteinMatch(possibleKey);
                if (proteinMatch.getPeptideMatches().contains(peptideKey)) {
                    proteinKey = possibleKey;
                    peptideShakerGUI.setSelectedItems(proteinKey, peptideKey, psmKey);
                    break;
                }
            }
        }

        if (!proteinKey.equals(PeptideShakerGUI.NO_SELECTION)) {
            proteinRow = getProteinRow(proteinKey);
        }

        if (proteinKeys.isEmpty()) {
            // For the silly people like me who happen to hide all proteins
            clearData();
            return;
        }

        if (proteinRow == -1) {
            peptideShakerGUI.resetSelectedItems();
        } else if (proteinTable.getSelectedRow() != proteinRow) {
            proteinTable.setRowSelectionInterval(proteinRow, proteinRow);
            proteinTable.scrollRectToVisible(proteinTable.getCellRect(proteinRow, 0, false));
            updatePeptideTable();
        }

        // invoke later to give time for components to update
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                int peptideRow = 0;
                String peptideKey = peptideShakerGUI.getSelectedPeptideKey();
                if (!peptideKey.equals(PeptideShakerGUI.NO_SELECTION)) {
                    peptideRow = getPeptideRow(peptideKey);
                }

                if (peptideTable.getSelectedRow() != peptideRow && peptideRow != -1) {
                    peptideTable.setRowSelectionInterval(peptideRow, peptideRow);
                    peptideTable.scrollRectToVisible(peptideTable.getCellRect(peptideRow, 0, false));
                    //peptideTableMouseReleased(null);
                }
            }
        });
    }

    /**
     * Returns the row of a desired protein.
     *
     * @param proteinKey the key of the protein
     * @return the row of the desired protein
     */
    private int getProteinRow(String proteinKey) {
        int modelIndex = proteinKeys.indexOf(proteinKey);
        if (modelIndex >= 0) {
            return proteinTable.convertRowIndexToView(modelIndex);
        } else {
            return -1;
        }
    }

    /**
     * Returns the row of a desired peptide.
     *
     * @param peptideKey the key of the peptide
     * @return the row of the desired peptide
     */
    private int getPeptideRow(String peptideKey) {
        int modelIndex = peptideKeys.indexOf(peptideKey);
        if (modelIndex >= 0) {
            return peptideTable.convertRowIndexToView(modelIndex);
        } else {
            return -1;
        }
    }

    /**
     * Clear all the data.
     */
    public void clearData() {

        proteinKeys.clear();
        peptideKeys.clear();

        DefaultTableModel peptideTableModel = (DefaultTableModel) peptideTable.getModel();
        peptideTableModel.getDataVector().removeAllElements();
        ProteinTableModel proteinTableModel = (ProteinTableModel) proteinTable.getModel();
        proteinTableModel.reset();

        peptideTableModel.fireTableDataChanged();
        proteinTableModel.fireTableDataChanged();

        ((TitledBorder) proteinPanel.getBorder()).setTitle("Proteins");
        proteinPanel.repaint();
        ((TitledBorder) peptidePanel.getBorder()).setTitle("Peptides");
        peptidePanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        proteinPanel = new javax.swing.JPanel();
        proteinTableScrollPane = new javax.swing.JScrollPane();
        proteinTable = new javax.swing.JTable();
        peptidePanel = new javax.swing.JPanel();
        peptideTableScrollPane = new javax.swing.JScrollPane();
        peptideTable = new javax.swing.JTable();
        disclamierPanel = new javax.swing.JPanel();
        disclaimerLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        proteinPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Proteins"));
        proteinPanel.setOpaque(false);

        proteinTable.setModel(new eu.isas.peptideshaker.gui.tablemodels.ProteinFractionTableModel());
        proteinTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        proteinTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                proteinTableMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                proteinTableMouseReleased(evt);
            }
        });
        proteinTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                proteinTableMouseMoved(evt);
            }
        });
        proteinTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                proteinTableKeyReleased(evt);
            }
        });
        proteinTableScrollPane.setViewportView(proteinTable);

        javax.swing.GroupLayout proteinPanelLayout = new javax.swing.GroupLayout(proteinPanel);
        proteinPanel.setLayout(proteinPanelLayout);
        proteinPanelLayout.setHorizontalGroup(
            proteinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proteinTableScrollPane)
                .addContainerGap())
        );
        proteinPanelLayout.setVerticalGroup(
            proteinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proteinPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proteinTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        peptidePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Peptides"));
        peptidePanel.setOpaque(false);

        peptideTable.setModel(new eu.isas.peptideshaker.gui.tablemodels.PeptideFractionTableModel());
        peptideTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                peptideTableMouseReleased(evt);
            }
        });
        peptideTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                peptideTableKeyReleased(evt);
            }
        });
        peptideTableScrollPane.setViewportView(peptideTable);

        javax.swing.GroupLayout peptidePanelLayout = new javax.swing.GroupLayout(peptidePanel);
        peptidePanel.setLayout(peptidePanelLayout);
        peptidePanelLayout.setHorizontalGroup(
            peptidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peptidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(peptideTableScrollPane)
                .addContainerGap())
        );
        peptidePanelLayout.setVerticalGroup(
            peptidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peptidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(peptideTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        disclamierPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Disclaimer"));
        disclamierPanel.setOpaque(false);

        disclaimerLabel.setText("<html>\nThe values above are estimations of the confidence of a peptide/protein if found in a fraction alone in the context of the whole analysis.<br>\n<i>These are <u><b>not</b></u> equal the confidence in the peptide/protein identifications when processing the fractions independently!</i><br><br>\nIndependant fractions (like different donors, measurements) or replicates should be processed separately.<br>\nTo ensure comparable fractions, verify that the PSM PEP against score plots are similar for all the fractions in the <i>Validation</i> tab.\n</html>");

        javax.swing.GroupLayout disclamierPanelLayout = new javax.swing.GroupLayout(disclamierPanel);
        disclamierPanel.setLayout(disclamierPanelLayout);
        disclamierPanelLayout.setHorizontalGroup(
            disclamierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(disclamierPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(disclaimerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(252, Short.MAX_VALUE))
        );
        disclamierPanelLayout.setVerticalGroup(
            disclamierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(disclamierPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(disclaimerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proteinPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(peptidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(disclamierPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(proteinPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(peptidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disclamierPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Update the selection.
     *
     * @param evt
     */
    private void proteinTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinTableMouseReleased
        updatePeptideTable();

        int row = proteinTable.getSelectedRow();
        int proteinIndex = proteinTable.convertRowIndexToModel(row);
        int column = proteinTable.getSelectedColumn();

        if (proteinIndex != -1) {
            // remember the selection
            newItemSelection();
        }

        if (evt != null && evt.getButton() == MouseEvent.BUTTON1 && proteinIndex != -1) {

            // open protein link in web browser
            if (column == proteinTable.getColumn("Accession").getModelIndex()
                    && ((String) proteinTable.getValueAt(row, column)).lastIndexOf("<html>") != -1) {

                String link = (String) proteinTable.getValueAt(row, column);
                link = link.substring(link.indexOf("\"") + 1);
                link = link.substring(0, link.indexOf("\""));

                this.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
                BareBonesBrowserLaunch.openURL(link);
                this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_proteinTableMouseReleased

    /**
     * Update the selection.
     *
     * @param evt
     */
    private void proteinTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_proteinTableKeyReleased
        updatePeptideTable();

        int row = proteinTable.getSelectedRow();
        int proteinIndex = proteinTable.convertRowIndexToModel(row);

        if (proteinIndex != -1) {
            // remember the selection
            newItemSelection();
        }
    }//GEN-LAST:event_proteinTableKeyReleased

    /**
     * Changes the cursor back to the default cursor.
     *
     * @param evt
     */
    private void proteinTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinTableMouseExited
        this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_proteinTableMouseExited

    /**
     * Changes the cursor into a hand cursor if the table cell contains an html
     * link.
     *
     * @param evt
     */
    private void proteinTableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proteinTableMouseMoved
        int row = proteinTable.rowAtPoint(evt.getPoint());
        int column = proteinTable.columnAtPoint(evt.getPoint());

        proteinTable.setToolTipText(null);

        if (row != -1 && column != -1 && column == proteinTable.getColumn("Accession").getModelIndex() && proteinTable.getValueAt(row, column) != null) {

            String tempValue = (String) proteinTable.getValueAt(row, column);

            if (tempValue.lastIndexOf("<html>") != -1) {
                this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            } else {
                this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        } else {
            this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_proteinTableMouseMoved

    /**
     * Remember the selection.
     *
     * @param evt
     */
    private void peptideTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peptideTableMouseReleased
        // remember the selection
        newItemSelection();
    }//GEN-LAST:event_peptideTableMouseReleased

    /**
     * Remember the selection.
     *
     * @param evt
     */
    private void peptideTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_peptideTableKeyReleased
        // remember the selection
        newItemSelection();
    }//GEN-LAST:event_peptideTableKeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel disclaimerLabel;
    private javax.swing.JPanel disclamierPanel;
    private javax.swing.JPanel peptidePanel;
    private javax.swing.JTable peptideTable;
    private javax.swing.JScrollPane peptideTableScrollPane;
    private javax.swing.JPanel proteinPanel;
    private javax.swing.JTable proteinTable;
    private javax.swing.JScrollPane proteinTableScrollPane;
    // End of variables declaration//GEN-END:variables

    /**
     * Displays or hide sparklines in the tables.
     *
     * @param showSparkLines boolean indicating whether sparklines shall be
     * displayed or hidden
     */
    public void showSparkLines(boolean showSparkLines) {

        for (int i = 2; i < proteinTable.getColumnCount() - 1; i++) {
            if (peptideShakerGUI.showSparklines()) {
                proteinTable.getColumn(proteinTable.getColumnName(i)).setCellRenderer(new JSparklinesBarChartTableCellRenderer(PlotOrientation.HORIZONTAL, 100.0, peptideShakerGUI.getSparklineColor()));
                ((JSparklinesBarChartTableCellRenderer) proteinTable.getColumn(proteinTable.getColumnName(i)).getCellRenderer()).showNumberAndChart(
                        true, peptideShakerGUI.getLabelWidth() - 20, peptideShakerGUI.getScoreAndConfidenceDecimalFormat());
            } else {
                ((JSparklinesBarChartTableCellRenderer) proteinTable.getColumn(proteinTable.getColumnName(i)).getCellRenderer()).showNumbers(true);
            }
        }
        
        for (int i = 2; i < peptideTable.getColumnCount() - 1; i++) {
            if (peptideShakerGUI.showSparklines()) {
                peptideTable.getColumn(peptideTable.getColumnName(i)).setCellRenderer(new JSparklinesBarChartTableCellRenderer(PlotOrientation.HORIZONTAL, 100.0, peptideShakerGUI.getSparklineColor()));
                ((JSparklinesBarChartTableCellRenderer) peptideTable.getColumn(peptideTable.getColumnName(i)).getCellRenderer()).showNumberAndChart(
                        true, peptideShakerGUI.getLabelWidth() - 20, peptideShakerGUI.getScoreAndConfidenceDecimalFormat());
            } else {
                ((JSparklinesBarChartTableCellRenderer) peptideTable.getColumn(peptideTable.getColumnName(i)).getCellRenderer()).showNumbers(true);
            }
        }


        proteinTable.revalidate();
        proteinTable.repaint();

        peptideTable.revalidate();
        peptideTable.repaint();
    }

    /**
     * Provides to the PeptideShakerGUI instance the currently selected protein,
     * peptide and psm.
     */
    public void newItemSelection() {

        String proteinKey = PeptideShakerGUI.NO_SELECTION;
        String peptideKey = PeptideShakerGUI.NO_SELECTION;
        String psmKey = PeptideShakerGUI.NO_SELECTION;

        if (proteinTable.getSelectedRow() != -1) {
            proteinKey = proteinKeys.get(proteinTable.convertRowIndexToModel(proteinTable.getSelectedRow()));
        }
        if (peptideTable.getSelectedRow() != -1) {
            peptideKey = peptideKeys.get(peptideTable.convertRowIndexToModel(peptideTable.getSelectedRow()));
        }

        peptideShakerGUI.setSelectedItems(proteinKey, peptideKey, psmKey);
    }

    /**
     * Returns a list of keys of the displayed proteins.
     *
     * @return a list of keys of the displayed proteins
     */
    public ArrayList<String> getDisplayedProteins() {
        return proteinKeys;
    }

    /**
     * Returns a list of keys of the displayed peptides.
     *
     * @return a list of keys of the displayed peptides
     */
    public ArrayList<String> getDisplayedPeptides() {
        return peptideKeys;
    }
}
