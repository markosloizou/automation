import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class FileTree extends JFrame implements TreeSelectionListener, TreeWillExpandListener
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
        //DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
        //DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("child 1");
        //DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("child 2");
        //root.add(child1);
        //root.add(child2);
        //create the tree by passing in the root node
        DefaultTreeModel treeModel = new DefaultTreeModel(root,true);
        tree = new JTree(treeModel);
        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);

        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);


        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);
        tree.addTreeWillExpandListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JTree Example");
        this.pack();
        this.setVisible(true);
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

        Object nodeInfo = node.getUserObject();
        TreeNode[] path = node.getPath();
        String filePath = "";
        for(TreeNode tn: path) {
            if(tn.toString() == "root") continue;
            else{
                filePath += "/" + tn.toString();
            }
        }
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
        System.out.println("Selected: " + node.toString());


    }

    private void LoadLazyChildren(TreeExpansionEvent e){

        DefaultMutableTreeNode last = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
        for(int i = 0; i < tree.getModel().getChildCount(last); i++){
            if(tree.getModel().getChildCount( tree.getModel().getChild(last, i)) > 0) continue;
            addChildren((DefaultMutableTreeNode) tree.getModel().getChild(last, i));
        }
        System.out.println("Expanding Node " + e.getPath().getLastPathComponent());
    }

    private void addChildren(DefaultMutableTreeNode node)
    {
        System.out.println("Adding children to: " + node.toString());
        TreeNode[] path = node.getPath();
        String filePath = "";
        for(TreeNode tn: path) {
            if(tn.toString() == "root") continue;
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

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileTree();
            }
        });
    }

    private String[] getSubdirectories(File file){
    String[] directories = file.list(new FilenameFilter() {
        @Override
        public boolean accept(File current, String name) {
            return new File(current, name).isDirectory();
            }
        });
        return directories;
    }

}