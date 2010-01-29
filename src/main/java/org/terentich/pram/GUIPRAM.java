package org.terentich.pram;

import java.awt.*;
import java.io.*;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.*;
import javax.swing.Action;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.CharEncoding;
import org.jdesktop.application.*;
import org.jdesktop.application.Task.BlockingScope;
import org.terentich.pram.tookit.MetaReader;

public class GUIPRAM extends SingleFrameApplication {

    private static final String APPLICATION_TITLE_FORMAT = "{0} {1}";

    private MetaReader          metaReader;

    private CLIPRAM             cli;
    private JTabbedPane         tabPane;

    @Override
    protected void initialize(String[] args) {
        cli = new CLIPRAM("pram");
        cli.parseArgs(args);
        readMetaInformation();

    }

    private void readMetaInformation() {
        metaReader = new MetaReader();
    }

    private void setContent() {

        FrameView mainview = getMainView();
        Action openFileAction = mainview.getContext().getActionMap()
                .get(ResourceNames.OPEN_FILE_ACTION_NAME);
        Action aboutDialogAction = mainview.getContext().getActionMap()
                .get(ResourceNames.ABOUT_DIALOG_ACTION_NAME);

        JMenuBar bar = new JMenuBar();

        JMenu menuFile = new JMenu(getContext().getResourceMap().getString(
                ResourceNames.MENU_FILE_NAME));
        JMenu menuHelp = new JMenu(getContext().getResourceMap().getString(
                ResourceNames.MENU_HELP_NAME));

        menuFile.add(openFileAction);
        menuHelp.add(aboutDialogAction);

        bar.add(menuFile);
        bar.add(menuHelp);
        mainview.setMenuBar(bar);

        // Container panel = mainview.getRootPane().getContentPane();
        // panel.setLayout(new MigLayout());
        // panel.add(new JButton(mainview.getContext().getActionMap()
        // .get(OPEN_FILE_ACTION_NAME)));

        mainview.getFrame().setTitle(
                MessageFormat.format(APPLICATION_TITLE_FORMAT,
                        metaReader.getApplicationName(),
                        metaReader.getApplicationVersion()));

        Container content = mainview.getRootPane().getContentPane();
        tabPane = new JTabbedPane();
        content.add(tabPane);

        JToolBar toolbar = new JToolBar();
        toolbar.add(getContext().getActionMap().get(
                ResourceNames.OPEN_FILE_ACTION_NAME));
        toolbar.add(getContext().getActionMap().get(
                ResourceNames.ABOUT_DIALOG_ACTION_NAME));
        content.add(toolbar, BorderLayout.NORTH);

        initCLIOptions();

    }

    @org.jdesktop.application.Action
    public void aboutDialog() {
        StringBuilder aboutMessage = new StringBuilder();
        aboutMessage.append(metaReader.getApplicationName() + "\n");
        aboutMessage.append("Description: "
                + metaReader.getApplicationDescription() + "\n");
        aboutMessage.append("License:    " + metaReader.getApplicationLicense()
                + "\n");
        aboutMessage.append("Version:    " + metaReader.getApplicationVersion()
                + "\n");
        aboutMessage.append("Vendor:     " + metaReader.getApplicationVendor()
                + "\n");
        aboutMessage.append("Vendor id:  "
                + metaReader.getApplicationVendorId() + "\n");
        aboutMessage.append("Build date: "
                + metaReader.getApplicationBuildDate() + "\n");
        aboutMessage.append("Build JDK:  "
                + metaReader.getApplicationBuildJDK() + "\n");
        aboutMessage.append("Built by:   " + metaReader.getApplicationBuiltBy()
                + "\n");
        aboutMessage.append("Revision:   "
                + metaReader.getApplicationRevision() + "\n");

        String[] libraries = metaReader.getApplicationClasspath()
                .replaceAll("libs/", "").split(" ");

        aboutMessage.append("Libraries: " + "\n");

        for (String library : libraries) {
            aboutMessage.append(library + "\n");
        }

        JOptionPane.showMessageDialog(getMainFrame(), aboutMessage);
    }

    @Override
    protected void startup() {

        // ResourceMap resources = getContext().getResourceMap(getClass());
        // UIManager.put("TextArea.font",
        // new FontUIResource(resources.getFont("font.editor")));

        setContent();
        show(getMainView());
    }

    private void initCLIOptions() {
        for (File file : cli.getInputFiles()) {
            showFileContent(file);
        }
    }

    @org.jdesktop.application.Action(block = BlockingScope.ACTION)
    public Task<Void, Void> openFile() {

        return new Task<Void, Void>(this) {

            @Override
            protected Void doInBackground() throws Exception {

                JFileChooser jfc = new JFileChooser(this.getContext()
                        .getResourceMap()
                        .getString(ResourceNames.OPEN_FILE_PATH));
                int answer = jfc.showOpenDialog(getMainFrame());

                if (JFileChooser.APPROVE_OPTION == answer) {
                    showFileContent(jfc.getSelectedFile());
                }

                return null;

            }

        };

    }

    private void showFileContent(File... files) {
        for (File file : files) {

            JPanel filePanel = new JPanel(new BorderLayout());
            JTextArea textbox = new JTextArea();
            filePanel.add(new JScrollPane(textbox));
            tabPane.add(file.getName(), filePanel);
            tabPane.setSelectedComponent(filePanel);

            try {
                @SuppressWarnings("unchecked")
                List<String> fileContent = FileUtils.readLines(file,
                        CharEncoding.UTF_8);

                for (String line : fileContent) {
                    textbox.append(line + "\n");
                }

                textbox.setCaretPosition(0);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
