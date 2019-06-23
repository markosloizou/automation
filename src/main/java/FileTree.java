import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;

import java.util.Arrays;

public class FileTree implements TreeSelectionListener, TreeWillExpandListener
{
    private JTree tree;


    public FileTree()
    {
        File []paths = File.listRoots();

        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] roots = fsv.getRoots();
        for (int i = 0; i < roots.length; i++)
        {
            System.out.println("Root: " + roots[i]);
        }

        //create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        for (File s:paths) {
            System.out.println(s.getAbsolutePath());
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(s.getAbsolutePath());
            root.add(node);
            addChildren(node);
        }



        Icons pair = new Icons().getMetal();

        UIManager.put("Tree.collapsedIcon", pair.collapsed);
        UIManager.put("Tree.expandedIcon", pair.expanded);

        //DefaultTreeModel treeModel = new DefaultTreeModel(root,true);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        tree = new JTree(treeModel);
//        JScrollPane scrollPane = new JScrollPane(tree);
//        content.add(scrollPane);
        //add(scrollPane);
        //Container.add(scrollPane, Integer.parseInt(BorderLayout.CENTER));

//        scrollPane.getViewport().setOpaque(false);
//        scrollPane.setOpaque(false);

        try{
        tree.setEditable(true);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.setOpaque(false);


        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        tree.addTreeWillExpandListener(this);
        tree.setCellRenderer(new MyCellRenderer());
/*
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.darkGray);
        this.setForeground(Color.gray);
        this.setTitle("JTree Example");
        this.pack();
        this.setSize(400,600);
        this.setVisible(true);*/
    }

    public JTree getTree(){
        return  tree;
    }

    public void valueChanged(TreeSelectionEvent e) {
//Returns the last path element of the selection.
//This method is useful only when the selection model allows a single selection.
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
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


    }

    private void LoadLazyChildren(TreeExpansionEvent e){

        DefaultMutableTreeNode last = (DefaultMutableTreeNode)e.getPath().getLastPathComponent(); //gets component that will expand
        for(int i = 0; i < tree.getModel().getChildCount(last); i++){
            if(tree.getModel().getChildCount( tree.getModel().getChild(last, i)) > 0) continue; //if it already has children continue
            addChildren((DefaultMutableTreeNode) tree.getModel().getChild(last, i)); //if no children add the children
        }
        System.out.println("Expanding Node " + e.getPath().getLastPathComponent());
    }

    private void addChildren(DefaultMutableTreeNode node)
    {
        System.out.println("Adding children to: " + node.toString());
        TreeNode[] path = node.getPath();
        String filePath = "";
        for(TreeNode tn: path) {
            if(tn.toString().equals("root")) continue;
            else{
                filePath += "/" + tn.toString();
            }
        }
        filePath = filePath + "/";

        File selected = new File(filePath);

        String[] subdirectories = getSubdirectories(selected);
        if(subdirectories == null){
            return;
        }
        Arrays.sort(subdirectories);
        for (String s:subdirectories) {
            if(s.charAt(0) == '.') continue;
            File f = new File(s);
            if(tree != null){
                DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
                model.insertNodeInto(new DefaultMutableTreeNode(f), node, node.getChildCount());
            }else{
                node.add(new DefaultMutableTreeNode(f));
            }
        }

    }


    public void treeWillExpand(TreeExpansionEvent e)  throws ExpandVetoException {
        LoadLazyChildren(e);
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }

    /*
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileTree();
            }
        });
    }*/

    private String[] getSubdirectories(File file){
    String[] directories = file.list(new FilenameFilter() {
        @Override
        public boolean accept(File current, String name) {
            return new File(current, name).isDirectory();
            }
        });
        return directories;
    }



    private class MyCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Color getBackgroundNonSelectionColor() {
            return Color.darkGray;
        }

        @Override
        public Color getBackgroundSelectionColor() {
            return Color.lightGray;
        }

        @Override
        public Color getBackground() {
            return Color.darkGray;
        }

        @Override
        public Color getTextSelectionColor(){
            return Color.white;
        }

        @Override
        public Color getTextNonSelectionColor(){
            return Color.white;
        }
/*
        @Override
        public  Icon getClosedIcon(){
            ImageIcon closedIcon = createImageIcon("folder-30.png");
            return closedIcon;
        }

        @Override
        public Icon getOpenIcon(){
            ImageIcon openIcon = createImageIcon("open-folder-30.png");
            return openIcon;
        }

        @Override
        public Icon getLeafIcon(){
            ImageIcon closedIcon = createImageIcon("folder-30.png");
            return closedIcon;
        }

        @Override
        public  Icon getDefaultOpenIcon(){
            ImageIcon dOpenIcon = createImageIcon("arrow-down-15.png");
            return dOpenIcon;

        }*/

        @Override
        public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
            final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
            this.setText(value.toString());
            return ret;
        }

        /** Returns an ImageIcon, or null if the path was invalid. */
        private  ImageIcon createImageIcon(String path) {
            java.net.URL imgURL = MyCellRenderer.class.getResource(path);
            if (imgURL != null) {
                System.out.println("Image URL: " + imgURL);

                return new ImageIcon(imgURL);

            } else {
                File f = new File(path);

                System.err.println("Couldn't find file: " + path + "\tAbsolute Path: " + f.getAbsolutePath() + "\tExists: " + f.isFile());
                System.out.println("Image URL: " + imgURL);
                return null;
            }
        }
    }

    public static final class Icons {

        private static final String COLLAPSED_ICON = "Tree.collapsedIcon";
        private static final String EXPANDED_ICON = "Tree.expandedIcon";
        private Icon collapsed;
        private Icon expanded;

        public static Icons getDefault() {
            Icons icons = new Icons();
            icons.collapsed = (Icon) UIManager.get(COLLAPSED_ICON);
            icons.expanded = (Icon) UIManager.get(EXPANDED_ICON);
            return new Icons();
        }

        public  Icons getMetal() {
            Icons icons = new Icons();
            try {
                icons.collapsed = (Icon) createImageIcon("arrow-right-15.png");
                icons.expanded = (Icon) createImageIcon("arrow-down-15.png");
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
            return icons;
        }

        private  ImageIcon createImageIcon(String path) {
            java.net.URL imgURL = Icons.class.getResource(path);
            if (imgURL != null) {
                System.out.println("Image URL: " + imgURL);

                return new ImageIcon(imgURL);

            } else {
                File f = new File(path);

                System.err.println("Couldn't find file: " + path + "\tAbsolute Path: " + f.getAbsolutePath() + "\tExists: " + f.isFile());
                System.out.println("Image URL: " + imgURL);
                return null;
            }
        }
    }

}