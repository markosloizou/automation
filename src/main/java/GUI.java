
import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    private JPanel imagePanel = new JPanel(new WrapLayout(0,20,20));
    private JPanel metadataPanel = new JPanel();
    private JPanel agenciesPanel = new JPanel();
    private JPanel topContainerPanel = new JPanel(new MigLayout());

    private JTextField imageTitleField = new JTextField(20);
    private JTextArea imageDescriptionArea = new JTextArea(5,20);
    private JTextArea imageKeywordsArea = new JTextArea(10,20);

    private JButton copyMetadataButton = new JButton("Copy");
    private JButton pasteMetadataButton = new JButton("Paste");
    private JButton syncMetadataButton = new JButton("Sync");

    private JTree fileTree = new FileTree().getTree();

    private ArrayList<JLabel> images = new ArrayList<JLabel>();
    private SwingWorker<ArrayList<JLabel>,JLabel> imageLoader = null;
    private  JScrollPane imagePanelScroll = null;
    private int imageWidth = 200;
    private int imageHeight = 200;

    public GUI(){
        frame = new JFrame("Micro Stock Automator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300,900);
        frame.addComponentListener(new frameResizeListener());

        //Tree Panel

        fileTree.addTreeSelectionListener(new treeSelectionListener());
        JScrollPane scrollPane = new JScrollPane(fileTree);
        treePanel.add(scrollPane);
        //add(scrollPane);
        //Container.add(scrollPane, Integer.parseInt(BorderLayout.CENTER));

        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        scrollPane.setMinimumSize(new Dimension(250,600));
        scrollPane.setPreferredSize(new Dimension(250,600));
        scrollPane.setMaximumSize(new Dimension(250,900));
        //treePanel.setPreferredSize(new Dimension(250,600));
        //treePanel.setMaximumSize(new Dimension(250,900));
        //treePanel.setMinimumSize(new Dimension(250,600));
        topContainerPanel.add(treePanel);



        //Image Panel

        JLabel imageLabel = new JLabel("Image Panel");
  //      imagePanel.setPreferredSize(new Dimension(700,600));
//        imagePanel.setMinimumSize(new Dimension(250,600));

        imagePanel.add(imageLabel);


        imagePanelScroll = new JScrollPane(imagePanel);
        imagePanelScroll.getVerticalScrollBar().setUnitIncrement(14);
        imagePanelScroll.setPreferredSize(new Dimension(700,600));
        imagePanelScroll.setMinimumSize(new Dimension(250,600));

        topContainerPanel.add(imagePanelScroll, "grow, push");




        //  Metadata Panel
        metadataPanel.setLayout(new MigLayout());
        metadataPanel.setPreferredSize(new Dimension(250,600));
        metadataPanel.setMaximumSize(new Dimension(250,900));
        metadataPanel.setMinimumSize(new Dimension(250,600));

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

        try{
            if(imageLoader != null) imageLoader.cancel(true);
            removeImages();
        }catch (Exception e){
            e.printStackTrace();
        }
        imageLoader = new SwingWorker<ArrayList<JLabel>, JLabel>() {

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
                            if(img == null) continue;
                            Dimension d = getImageDimensions(img);
                            Image dimg = img.getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH);
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
                    images.add(l);
                }
                imagePanel.revalidate();
                imagePanel.repaint();
                imagePanelScroll.revalidate();

            }


            @Override
            protected void done() {
                System.out.println("Done Loading " + images.size() +" Images");
                imageTitleField.setText("Done Loading");
                imagePanel.validate();
               // imagePanel.repaint();
            }
        };

        imageLoader.execute();
    }

    private Dimension getImageDimensions(BufferedImage img){
        if(img == null) return new Dimension(0,0);
        int imgH =  img.getHeight();
        int imgW = img.getWidth();
        float aspectR = (float)imgW/imgH;
        Dimension d;

        if(imgH > imgW){
            d =  new Dimension( (int) (imageHeight*aspectR) ,imageHeight);
        }else{
            d = new Dimension(imageWidth, (int)(imageHeight/aspectR));
        }
        System.out.println("Original Dimensions: W = " + imgW + " H = " + imgH);
        System.out.println("New Dimensions: W = " + d.width + " H = " + d.height + " AR = " + aspectR);
        return d;
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
        imagePanel.removeAll();
        imagePanel.revalidate();
        imagePanel.repaint();
        imagePanelScroll.revalidate();
    }

    private boolean isImage(File f){
        String ext = FilenameUtils.getExtension(f.getAbsolutePath());
        if(ext.equals("jpg")) return true;
        else if(ext.equals("jpeg")) return true;
        else if(ext.equals("tiff")) return true;
        else if(ext.equals("png")) return  true;
        else return false;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
              GUI g = new GUI();
                g.newDirectorySelection("/home/markos/Pictures");
            }
        });


    }

    class frameResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            // Recalculate the variable you mentioned
            /*int w = frame.getWidth() - 250*2 - 50;
            int n = (int) Math.floor((float )w / (float)(imageWidth+30));
            if(n <= 0 ) n = 1;
            System.out.println("W = " + w );
            System.out.println("Number of columns: " + n);
            GridLayout gl = (GridLayout) imagePanel.getLayout();
            gl.setColumns(n);

            imagePanel.revalidate();*/
        }
    }

    class treeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
            if(node == null) return;

/*
        if (node == null)
            //Nothing is selected.
            return;
*/
            Object nodeInfo = node.getUserObject();
            TreeNode[] path = node.getPath();
            String filePath = "";
            for(TreeNode tn: path) {
                if(tn.toString().equals("root")) continue;
                else if(tn.toString().equals("/")) continue;
                else{
                    filePath += "/" + tn.toString();
                }
            }
        /*
        File selected = new File(filePath);


        String[] subdirectories = getSubdirectories(selected);
        if(subdirectories == null){
            System.out.println("Selected: " + selected.toString());
            return;
        }
        for (String s:subdirectories) {
            File f = new File(s);
            node.add(new DefaultMutableTreeNode(f));
        }

*/
            System.out.println("Selected: " + node.toString() + "\t Path: " + filePath );


            newDirectorySelection(filePath);

        }
    }


    private class ftpConcreteObserver implements FTPObserver{

        public ftpConcreteObserver(){
            ftpUploadStateSubject.getInstance().attachObserver(this); //make itself an observer
        }

        @Override
        public void update() {
            //TODO get state and write to GUI agencies

            //First get Agencies

            //Then get states of each agency and update agency by agency

        }

        @Override
        public void update(Agency a) {
            //Get state of single agency and update
        }
    }

}
