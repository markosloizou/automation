
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.List;

public class GUI {
    private JFrame frame;
    private JPanel treePanel = new JPanel();
    private JPanel imagePanel = new JPanel();
    private JPanel metadataPanel = new JPanel();
    private JPanel agenciesPanel = new JPanel();
    private JPanel topContainerPanel = new JPanel(new MigLayout());

    private JTextField imageTitleField = new JTextField(20);
    private JTextArea imageDescriptionArea = new JTextArea(5,20);
    private JTextArea imageKeywordsArea = new JTextArea(10,20);

    private JButton copyMetadataButton = new JButton("Copy");
    private JButton pasteMetadataButton = new JButton("Paste");
    private JButton syncMetadataButton = new JButton("Sync");

    private ArrayList<JLabel> images = new ArrayList<JLabel>();

    private int imageWidth = 200;
    private int imageHeight = 200;

    public GUI(){
        frame = new JFrame("Micro Stock Automator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300,900);

        //Tree Panel

        JScrollPane scrollPane = new JScrollPane(new FileTree().getTree());
        treePanel.add(scrollPane);
        //add(scrollPane);
        //Container.add(scrollPane, Integer.parseInt(BorderLayout.CENTER));

        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        scrollPane.setMinimumSize(new Dimension(250,600));
        scrollPane.setPreferredSize(new Dimension(250,600));
        scrollPane.setMaximumSize(new Dimension(250,600));
        treePanel.setPreferredSize(new Dimension(250,600));
        treePanel.setMaximumSize(new Dimension(250,600));
        topContainerPanel.add(treePanel);



        //Image Panel

        JLabel imageLabel = new JLabel("Image Panel");
        imagePanel.setPreferredSize(new Dimension(700,600));
        imagePanel.add(imageLabel);


        topContainerPanel.add(imagePanel, "pushx, growx");




        //  Metadata Panel
        metadataPanel.setLayout(new MigLayout());
        metadataPanel.setPreferredSize(new Dimension(250,600));
        metadataPanel.setMaximumSize(new Dimension(250,600));
        JLabel metadataLabel = new JLabel("Metadata Panel");
        metadataPanel.add(metadataLabel, "wrap");

        JLabel titleLabel = new JLabel("Title: ");
        metadataPanel.add(titleLabel, "wrap");
        metadataPanel.add(imageTitleField, "wrap");

        JLabel descriptionLabel = new JLabel("Description");
        metadataPanel.add(descriptionLabel,"wrap");
        imageKeywordsArea.setLineWrap(true);
        imageKeywordsArea.setWrapStyleWord(true);
        metadataPanel.add(imageDescriptionArea,"wrap");

        JLabel keywordsLabel = new JLabel("Keywords");
        metadataPanel.add(keywordsLabel,"wrap");
        imageKeywordsArea.setLineWrap(true);
        imageKeywordsArea.setWrapStyleWord(true);
        metadataPanel.add(new JScrollPane(imageKeywordsArea),"wrap");

        metadataPanel.add(copyMetadataButton, "split 3, pushx, growx");
        metadataPanel.add(pasteMetadataButton, "pushx, growx");
        metadataPanel.add(syncMetadataButton, "pushx, growx, gapright 15");




        topContainerPanel.add(metadataPanel);

        frame.getContentPane().add(BorderLayout.NORTH, topContainerPanel);

        JLabel agenciesLabel = new JLabel("Agencies Panel");
        agenciesPanel.add(agenciesLabel);
        frame.getContentPane().add(BorderLayout.SOUTH,agenciesPanel);



        //JButton button = new JButton("Press");
        //frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
    }


    public void newDirectorySelection(final String directory){
       /* if(SwingUtilities.isEventDispatchThread()){
            loadImages(directory);
        }
        else{
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    loadImages(directory);
                }
            });
        }*/

        SwingWorker<ArrayList<JLabel>,JLabel> sw = new SwingWorker<ArrayList<JLabel>, JLabel>() {

            @Override
            protected ArrayList<JLabel> doInBackground() throws Exception {
                ArrayList<JLabel> jl = new ArrayList<JLabel>();
                System.out.println("In swing worker");
                File selected = new File(directory);
                File[] files = selected.listFiles();
                if(files == null){
                    return jl;
                }
                Arrays.sort(files);
                for(File f: files){
                    if(isImage(f)){
                        try {
                            JLabel l = new JLabel(f.getName());
                            BufferedImage img = null;
                            try {
                                img = ImageIO.read(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Image dimg = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_FAST);
                            Icon icon = new ImageIcon(dimg);
                            l.setIcon(icon);
                            l.setHorizontalTextPosition(JLabel.CENTER);
                            l.setVerticalTextPosition(JLabel.BOTTOM);
                            jl.add(l);
                            publish(l);
                        } catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }


                return jl;
            }

            @Override
            protected void process(List<JLabel> chunks) {
                super.process(chunks);
                for (JLabel l : chunks) {
                    imagePanel.add(l);
                }
                imagePanel.validate();
            }


            @Override
            protected void done() {
                removeImages();
                try {
                    ArrayList<JLabel> labels = get();
                    images = labels;
                    imagePanel.add(new JLabel("Done Loading"));
                    System.out.println("Done Loading " + labels.size() +" Images");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                imageTitleField.setText("Done Loading");
                imagePanel.validate();
               // imagePanel.repaint();
            }
        };

        sw.execute();
    }

    private void loadImages(String directory){
        removeImages();
        File selected = new File(directory);
        File[] files = selected.listFiles();
        if(files == null){
            return;
        }
        Arrays.sort(files);
        for(File f: files){
            if(isImage(f)){
                try {
                    JLabel l = new JLabel(f.getName());
                    BufferedImage img = null;
                    try {
                        img = ImageIO.read(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Image dimg = img.getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                    Icon icon = new ImageIcon(dimg);
                    l.setIcon(icon);
                    images.add(l);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }

        for(JLabel l: images){
            imagePanel.add(l);
        }
    }

    private void removeImages(){
        for(JLabel l: images){
            if(l.getParent() == imagePanel) imagePanel.remove(l);
        }
    }

    private boolean isImage(File f){
        String ext = FilenameUtils.getExtension(f.getAbsolutePath());
        if(ext.equals("jpg")) return true;
        else if(ext.equals("jpeg")) return true;
        else if(ext.equals("tiff")) return true;
        else return false;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
              GUI g = new GUI();
                g.newDirectorySelection("/home/markos/IdeaProjects/stockautomator/test_images");
            }
        });


    }


}
