package br.com.app.mergedpdf;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Guilherme Monteiro
 */
public class ModelTablePDFs extends DefaultTableModel {

    // Define column names
    private static final String[] COLUMN_NAMES = {"Selecionar", "Nome do Arquivo", "Arquivo"};

    // Define column data types
    private static final Class<?>[] COLUMN_TYPES = {Boolean.class, String.class, String.class};

    // Creates new ModelTablePDFs
    public ModelTablePDFs() {
        super(COLUMN_NAMES, 0); // Initialize the model with columns and 0 rows
    }

    /**
     * Override the getColumnClass method to return the data type of each
     * column.
     *
     * @param columnIndex
     * @return
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_TYPES[columnIndex];
    }

    /**
     *
     * Add a method to add a line with checkbox and name.
     *
     * @param selected
     * @param name
     */
    public void addRowWithCheckboxAndName(boolean selected, String name, String file) {
        Object[] rowData = {selected, name, file};
        addRow(rowData);
    }

    /**
     *
     * Override the isCellEditable method to make the cells from the first
     * editable column (checkbox).
     *
     * @param row
     * @param column
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0;
    }

    /**
     * Sets the size of the table column.
     *
     * @param table
     * @param columnIdex
     * @param width
     */
    public void setColumnWidth(JTable table, int columnIdex, int width) {
        TableColumn column = table.getColumnModel().getColumn(columnIdex);
        column.setPreferredWidth(width);
    }

    /**
     * Remove all rows selecteds.
     *
     * @param table
     */
    public void removeRowsSelected(JTable table) {
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            boolean line = (boolean) table.getValueAt(i, 0);
            if (line) {
                removeRow(i);
            }
        }
    }

    /**
     * Takes all selected files and returns a vector of files.
     *
     * @param table
     * @param fileColumnIndex
     * @return
     */
    public String[] getFilesCheckBoxSelected(JTable table, int fileColumnIndex) {
        String[] files = new String[countRowsCheckBoxSelected(table, fileColumnIndex)];
        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            boolean line = (boolean) table.getValueAt(i, 0);
            if (line) {
                Object fileObject = getValueAt(i, fileColumnIndex);
                if (fileObject instanceof String) {
                    files[i] += fileObject;
                }
            }
        }
        return files;
    }

    /**
     * This method counts how many items are selected in the table.
     *
     * @param table
     * @param fileColumnIndex
     * @return
     */
    public int countRowsCheckBoxSelected(JTable table, int fileColumnIndex) {
        int count = 0;

        for (int i = table.getRowCount() - 1; i >= 0; i--) {
            boolean line = (boolean) table.getValueAt(i, 0);
            if (line) {
                count++;
            }
        }

        return count;
    }

    /**
     * Moves the selected line.
     *
     * @param table
     * @param direction
     */
    public void moveSelectedRows(JTable table, int direction) {
        int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
            int index = selectedRows[i];
            if (direction < 0 && index > 0) {
                this.moveRow(index, index, index + direction);
                table.getSelectionModel().clearSelection(); // Clear selection
                table.getSelectionModel().addSelectionInterval(index - 1, index - 1); // Select row moved
            } else if (direction > 0 && index < this.getRowCount() - 1) {
                this.moveRow(index, index, index + direction);
                table.getSelectionModel().clearSelection(); // Clear selection
                table.getSelectionModel().addSelectionInterval(index + 1, index + 1); // Seleciona a linha movida
            }
        }
    }
}
