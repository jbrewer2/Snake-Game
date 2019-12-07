//Copyright Martin Osborne and Ken Lambert 1998-2001
//All rights reserved

package BreezySwing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The class GBPanel (short for Grid Bag Panel) provides a high-level panel.
 * All panels come with built-in mouse event handling for motion, clicks,
 * presses, releases, and dragging. Some panels come with event handling for
 * buttons and list boxes.
 * </pre>
 */
public class GBPanel extends JPanel{

   GridBagLayout gbl = new GridBagLayout();
   GridBagConstraints gbc = new GridBagConstraints();

   // Potential reference to parent container
   GBFrame myFrame = null;
   GBDialog myDialog = null;
   GBApplet myApplet = null;

/**
 * Creates a panel; receives "parent" frame as argument, for event handling
 * with buttons and list boxes.
 */
   public GBPanel(GBFrame frame){
      this();
      myFrame = frame;
   }

/**
 * Creates a panel; receives "parent" dialog as argument, for event handling
 * with buttons and list boxes.
 */
   public GBPanel(GBDialog dialog){
      this();
      myDialog = dialog;
   }

/**
 * Creates a panel; receives "parent" applet as argument, for event handling
 * with buttons and list boxes.
 */
   public GBPanel(GBApplet applet){
      this();
      myApplet = applet;
   }

/**
 * Creates a panel without a "parent" frame, used just for graphics
 * with no buttons or list boxes.
 */
   public GBPanel(){
      setLayout(gbl);
      gbc.weightx = 100;
      gbc.weighty = 100;
      gbc.insets.bottom = 1;
      gbc.insets.left = 2;
      gbc.insets.right = 2;
      gbc.insets.top = 1;
      addMouseListener(new GBPanMouseListener(this));
      addMouseMotionListener(new GBPanMouseMotionListener(this));
   }


/**
 * The GBPanel subclass must implement this method in order to handle mouse clicks in
 * the panel.
 * If no event handling is desired, this method need no be implemented.
 * @param  x The x coordinate of the mouse in the panel.
 * @param  y The y coordinate of the mouse in the panel.
 */
  public void mouseClicked(int x, int y){}

/**
 * The GBPanel subclass must implement this method in order to handle mouse pressed events in
 * the panel.
 * If no event handling is desired, this method need no be implemented.
 * @param  x The x coordinate of the mouse in the panel.
 * @param  y The y coordinate of the mouse in the panel.
 */
   public void mousePressed(int x, int y){}

/**
 * The GBPanel subclass implement this method in order to handle mouse released events in
 * the panel.
 * If no event handling is desired, this method need no be implemented.
 * @param  x The x coordinate of the mouse in the panel.
 * @param  y The y coordinate of the mouse in the panel.
 */
   public void mouseReleased(int x, int y){}

   /**
 * The GBPanel subclass implement this method in order to handle mouse entered events in
 * the panel.
 * If no event handling is desired, this method need no be implemented.
 * @param  x The x coordinate of the mouse in the panel.
 * @param  y The y coordinate of the mouse in the panel.
 */
   public void mouseEntered(int x, int y){}
/**
 * The GBPanel subclass implement this method in order to handle mouse exited events in
 * the panel.
 * If no event handling is desired, this method need no be implemented.
 * @param  x The x coordinate of the mouse in the panel.
 * @param  y The y coordinate of the mouse in the panel.
 */
   public void mouseExited(int x, int y){}

/**
 * The GBPanel subclass implement this method in order to handle mouse moved events in
 * the panel.
 * If no event handling is desired, this method need no be implemented.
 * @param  x The x coordinate of the mouse in the panel.
 * @param  y The y coordinate of the mouse in the panel.
 */
   public void mouseMoved(int x, int y){}

/**
 * The GBPanel subclass implement this method in order to handle mouse dragged events in
 * the panel.
 * If no event handling is desired, this method need no be implemented.
 * @param  x The x coordinate of the mouse in the panel.
 * @param  y The y coordinate of the mouse in the panel.
 */
   public void mouseDragged(int x, int y){}

/**
 * Adds a label with the specified name to the specified position, with the specified
 * width and height.
 * @param  text The name of the label.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the label is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the label is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the label.
 * @param  height The number of rows of the panel's grid occuppied by the label.
 * @return  the JLabel
 */
    public JLabel addLabel (String text, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      JLabel control = new JLabel (text);
      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds a JTextField containing the specified string to the specified position, with the specified
 * width and height.
 * @param  text The string to be displayed initially.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the JTextField is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the JTextField is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the JTextField.
 * @param  height The number of rows of the panel's grid occuppied by the JTextField.
 * @return the JTextField.
 */
    public JTextField addTextField (String text, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      JTextField control = new JTextField (text);
      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds a JTextArea containing the specified string to the specified position, with the specified
 * width and height.
 * @param  text The string to be displayed initially.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the JTextArea is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the JTextArea is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the JTextArea.
 * @param  height The number of rows of the panel's grid occuppied by the JTextArea.
 * @return the TextArea.
 */
    public JTextArea addTextArea (String text, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.BOTH;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      gbc.weightx = 500;
      gbc.weighty = 500;
      JTextArea control = new JTextArea (text); //, height*2, width*15);
      add (new JScrollPane(control), row, col, width, height);
      gbc.weightx = 100;
      gbc.weighty = 100;
      control.setFont (new Font ("Courier", Font.PLAIN, 12));
      return control;
   }

/**
 * Adds a JList (a scrolling list) to the specified position, with the specified
 * width and height.
 * @param  row The beginning row (starting from 1) of the window's grid in which the JList is displayed.
 * @param  col The beginning column (starting from 1) of the window's grid in which the JList is displayed.
 * @param  width The number of columns of the window's grid occuppied by the JList.
 * @param  height The number of rows of the window's grid occuppied by the JList.
 * @return the JList
 */
    public JList addList (int row, int col, int width, int height){
      // Throw exception if parent container is missing.
      if (myFrame == null && myDialog == null && myApplet == null)

          throw new RuntimeException("Cannot add button to this panel");
      gbc.fill = GridBagConstraints.BOTH;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      gbc.weightx = 500;
      gbc.weighty = 500;
      JList control = new JList (new DefaultListModel()); //height*2, false);
      control.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      // Attach listener from parent class to this list box
      MouseListener listener;
      if (myFrame != null)
          listener = new GBFrameDCListener(control, myFrame);
      else if (myDialog != null)
          listener = new GBDialogDCListener(control, myDialog);
      else
          listener = new GBAppletDCListener(control, myApplet);
      control.addMouseListener(listener);
      add (new JScrollPane(control), row, col, width, height);
      gbc.weightx = 100;
      gbc.weighty = 100;
      return control;
   }

/**
 * Adds a JComboBox (pull down list) to the specified position, with the specified
 * width and height.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the JComboBox is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the JComboBox is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the JComboBox.
 * @param  height The number of rows of the panel's grid occuppied by the JComboBox.
 * @return the JComboBox.
 */
    public JComboBox addComboBox (int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      JComboBox control = new JComboBox();
      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds an IntegerField containing the specified integer to the specified position, with the specified
 * width and height.
 * @param  num The integer to be displayed initially.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the IntegerField is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the IntegerField is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the IntegerField.
 * @param  height The number of rows of the panel's grid occuppied by the IntegerField.
 * @return the IntegerField.
 */
    public IntegerField addIntegerField (int num, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      IntegerField control = new IntegerField (num);
      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds a DoubleField containing the specified number to the specified position, with the specified
 * width and height.
 * @param  num The number to be displayed initially.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the DoubleField is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the DoubleField is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the DoubleField.
 * @param  height The number of rows of the panel's grid occuppied by the DoubleField.
 * @return the DoubleField.
 */
    public DoubleField addDoubleField (double num, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      DoubleField control = new DoubleField (num);
      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds a JCheckBox to the specified position, with the specified
 * width and height.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the JCheckBox is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the JCheckBox is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the JCheckBox.
 * @param  height The number of rows of the panel's grid occuppied by the JCheckBox.
 * @return the JCheckBox.
 */
    public JCheckBox addCheckBox (String text, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      JCheckBox control = new JCheckBox (text);
      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds a JRadioButton to the specified position, with the specified
 * width and height.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the JRadioButton is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the JRadioButton is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the JRadioButton.
 * @param  height The number of rows of the panel's grid occuppied by the JRadioButton.
 * @return the JRadioButton.
 */
    public JRadioButton addRadioButton (String text, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      JRadioButton control = new JRadioButton (text);
      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds a JButton with the specified name to the specified position, with the specified
 * width and height.
 * @param  text The name of the Button.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the Button is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the Button is displayed.
 * @param  width The number of columns of the panel's grid occuppied by the Button.
 * @param  height The number of rows of the panel's grid occuppied by the Button.
 * @return the button.
 * Example:
 * <pre>
 *    JButton okButton = addButton("OK", 1, 1, 1, 1);
 *    // Adds a button at position row 1, column 1,
 *    // with a width of 1 column and a height of one row.
 * </pre>
 */
    public JButton addButton (String text, int row, int col, int width, int height){
      // Throw exception if parent container is missing.
      if (myFrame == null && myDialog == null && myApplet == null)
          throw new RuntimeException("Cannot add button to this panel");

      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.CENTER;
      JButton control = new JButton (text);

      // Attach listener from parent class to this button
      ActionListener listener;
      if (myFrame != null)
          listener = new GBFrameButtonListener(myFrame);
      else if (myDialog != null)
          listener = new GBDialogButtonListener(myDialog);
      else
          listener = new GBAppletButtonListener(myApplet);
      control.addActionListener(listener);

      add (control, row, col, width, height);
      return control;
   }

/**
 * Adds a GBPanel to the specified position, with the specified
 * width and height, with no capability to handle button events or list box events.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the GBPanel is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the GBPanel is displayed.
 * @param  width The number of columns of the panel's grid occupied by the GBPanel.
 * @param  height The number of rows of the panels's grid occupied by the GBPanel.
 * @return the GBPanel.
 */
    public GBPanel addPanel(GBPanel panel, int row, int col, int width, int height){
      gbc.fill = GridBagConstraints.BOTH;
      gbc.anchor = GridBagConstraints.NORTHWEST;
      gbc.weightx = 500;
      gbc.weighty = 500;
      add (panel, row, col, width, height);
      gbc.weightx = 100;
      gbc.weighty = 100;
      return panel;
   }

/**
 * Creates a GBPanel and adds it to the specified position, with the specified
 * width and height, and connects the panel to this panel's frame, dialog, or applet 
 * for event handling with buttons and list boxes.
 * @param  row The beginning row (starting from 1) of the panel's grid in which the GBPanel is displayed.
 * @param  col The beginning column (starting from 1) of the panel's grid in which the GBPanel is displayed.
 * @param  width The number of columns of the panel's grid occupied by the GBPanel.
 * @param  height The number of rows of the panel's grid occupied by the GBPanel.
 * @return the GBPanel.
 */
   public GBPanel addPanel(int row, int col, int width, int height){
      // Throw exception if parent container is missing.
      if (myFrame == null && myDialog == null && myApplet == null)
          throw new RuntimeException("Cannot add button or list box to this panel");
      if (myFrame != null)
          return addPanel(new GBPanel(myFrame), row, col, width, height);
      else if (myDialog != null)
          return addPanel(new GBPanel(myDialog), row, col, width, height);
      else
          return addPanel(new GBPanel(myApplet), row, col, width, height);
   }

   private void add( Component c,int y, int x, int w, int h){
      gbc.gridx = x-1;
      gbc.gridy = y-1;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      gbl.setConstraints(c, gbc);
      add(c);
   }
}

// Controller class to handle mouse events clicked, pressed, and released.

class GBPanMouseListener extends MouseAdapter{

   GBPanel myPanel;

   public GBPanMouseListener(GBPanel pan){
      myPanel = pan;
   }

   public void mouseClicked(MouseEvent e){
      myPanel.mouseClicked(e.getX(), e.getY());
   }

   public void mousePressed(MouseEvent e){
      myPanel.mousePressed(e.getX(), e.getY());
   }

   public void mouseReleased(MouseEvent e){
      myPanel.mouseReleased(e.getX(), e.getY());
   }

   public void mouseEntered(MouseEvent e){
      myPanel.mouseEntered(e.getX(), e.getY());
   }

   public void mouseExited(MouseEvent e){
      myPanel.mouseExited(e.getX(), e.getY());
   }
}

// Controller class to handle mouse motion events moved and dragged.

class GBPanMouseMotionListener extends MouseMotionAdapter{

   GBPanel myPanel;

   public GBPanMouseMotionListener(GBPanel pan){
      myPanel = pan;
   }

   public void mouseMoved(MouseEvent e){
      myPanel.mouseMoved(e.getX(), e.getY());
   }

   public void mouseDragged(MouseEvent e){
      myPanel.mouseDragged(e.getX(), e.getY());
   }
}



