package net.retrocarnage.editor.assetmanager.batchimport;

import javax.swing.JPanel;

public final class BatchImportVisualPanel1 extends JPanel {

    /**
     * Creates new form BatchImportVisualPanel1
     */
    public BatchImportVisualPanel1() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Meta data";
    }

    String getTags() {
        return txtSpriteTags.getText();
    }

    void setTags(final String tags) {
        txtSpriteTags.setText(tags);
    }

    boolean isTile() {
        return chkTile.isSelected();
    }

    void setTile(final boolean tile) {
        chkTile.setSelected(tile);
    }

    String getAuthor() {
        return txtSpriteAuthor.getText();
    }

    void setAuthor(final String author) {
        txtSpriteAuthor.setText(author);
    }

    String getWebsite() {
        return txtSpriteWebsite.getText();
    }

    void setWebsite(final String website) {
        txtSpriteWebsite.setText(website);
    }

    String getLicenseLink() {
        return txtSpriteLicenseLink.getText();
    }

    void setLicenseLink(final String link) {
        txtSpriteLicenseLink.setText(link);
    }

    String getLicenseText() {
        return txtSpriteLicenseText.getText();
    }

    void setLicenseText(final String text) {
        txtSpriteLicenseText.setText(text);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblSpriteTags = new javax.swing.JLabel();
        txtSpriteTags = new javax.swing.JTextField();
        lblTile = new javax.swing.JLabel();
        chkTile = new javax.swing.JCheckBox();
        pnlSpriteAttribution = new javax.swing.JPanel();
        lblSpriteAuthor = new javax.swing.JLabel();
        lblSpriteWebsite = new javax.swing.JLabel();
        lblSpriteLicenseLink = new javax.swing.JLabel();
        lblSpriteLicenseText = new javax.swing.JLabel();
        txtSpriteAuthor = new javax.swing.JTextField();
        txtSpriteWebsite = new javax.swing.JTextField();
        txtSpriteLicenseLink = new javax.swing.JTextField();
        txtSpriteLicenseText = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblSpriteTags, org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.lblSpriteTags.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        add(lblSpriteTags, gridBagConstraints);

        txtSpriteTags.setText(org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.txtSpriteTags.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(txtSpriteTags, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblTile, org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.lblTile.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        add(lblTile, gridBagConstraints);

        chkTile.setPreferredSize(new java.awt.Dimension(18, 39));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(chkTile, gridBagConstraints);

        pnlSpriteAttribution.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.pnlSpriteAttribution.border.title"))); // NOI18N
        pnlSpriteAttribution.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(lblSpriteAuthor, org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.lblSpriteAuthor.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        pnlSpriteAttribution.add(lblSpriteAuthor, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblSpriteWebsite, org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.lblSpriteWebsite.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        pnlSpriteAttribution.add(lblSpriteWebsite, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblSpriteLicenseLink, org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.lblSpriteLicenseLink.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        pnlSpriteAttribution.add(lblSpriteLicenseLink, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lblSpriteLicenseText, org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.lblSpriteLicenseText.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 10);
        pnlSpriteAttribution.add(lblSpriteLicenseText, gridBagConstraints);

        txtSpriteAuthor.setText(org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.txtSpriteAuthor.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlSpriteAttribution.add(txtSpriteAuthor, gridBagConstraints);

        txtSpriteWebsite.setText(org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.txtSpriteWebsite.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlSpriteAttribution.add(txtSpriteWebsite, gridBagConstraints);

        txtSpriteLicenseLink.setText(org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.txtSpriteLicenseLink.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlSpriteAttribution.add(txtSpriteLicenseLink, gridBagConstraints);

        txtSpriteLicenseText.setText(org.openide.util.NbBundle.getMessage(BatchImportVisualPanel1.class, "BatchImportVisualPanel1.txtSpriteLicenseText.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlSpriteAttribution.add(txtSpriteLicenseText, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 2, 2, 2);
        add(pnlSpriteAttribution, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkTile;
    private javax.swing.JLabel lblSpriteAuthor;
    private javax.swing.JLabel lblSpriteLicenseLink;
    private javax.swing.JLabel lblSpriteLicenseText;
    private javax.swing.JLabel lblSpriteTags;
    private javax.swing.JLabel lblSpriteWebsite;
    private javax.swing.JLabel lblTile;
    private javax.swing.JPanel pnlSpriteAttribution;
    private javax.swing.JTextField txtSpriteAuthor;
    private javax.swing.JTextField txtSpriteLicenseLink;
    private javax.swing.JTextField txtSpriteLicenseText;
    private javax.swing.JTextField txtSpriteTags;
    private javax.swing.JTextField txtSpriteWebsite;
    // End of variables declaration//GEN-END:variables
}
